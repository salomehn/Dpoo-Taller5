package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import uniandes.dpoo.hamburguesas.excepciones.HamburguesaException;
import uniandes.dpoo.hamburguesas.excepciones.IngredienteRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.NoHayPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoFaltanteException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.YaHayUnPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import uniandes.dpoo.hamburguesas.mundo.Restaurante;

public class RestauranteTest {
	
	private Restaurante restaurante;

	
	@BeforeEach
    void setUp() throws Exception {
		
        restaurante = new Restaurante();
    }
	@AfterEach
	void tearDown() throws Exception {
		
		restaurante = null; 
    }
	
    @Test
    @DisplayName("Probando inciar nuevo pedido")
    
    void testIniciarPedido() throws YaHayUnPedidoEnCursoException {
    	
    	 assertNull(restaurante.getPedidoEnCurso(), "la prueba fallo: Existe un pedido en curso");
    	 restaurante.iniciarPedido( "Ana", "Avenida 147 #27-92" );
    	  
    	 Pedido pedido = restaurante.getPedidoEnCurso();
    	 
    	 assertNotNull(pedido, "Existe un pedido activo");
    	    assertThrows(YaHayUnPedidoEnCursoException.class,
    	        () -> restaurante.iniciarPedido("Maria", "Calle 52 #27-82, torre a"),
    	        "Lanza una excepción porque ya habíamos iniciado un pedido");
  
    }
    
    @Test
    @DisplayName("Probando cerrar y guardar pedido")
    void testCerrarYGuardarPedido() throws NoHayPedidoEnCursoException, IOException, YaHayUnPedidoEnCursoException {
    	
    	assertNull(restaurante.getPedidoEnCurso(), "la prueba fallo: Existe un pedido en curso");
    	
    	restaurante.iniciarPedido( "Juan", "Carrera 10 #07-27" );
    	
    	Pedido pedido = restaurante.getPedidoEnCurso(); 
    	String nombreArchivo = "factura_" + pedido.getIdPedido( ) + ".txt";
    	 
    	File carpeta = new File("data/facturas");
    	File archivoFactura = new File(carpeta, nombreArchivo);
 	    
 	   	ProductoMenu productoMenu = new ProductoMenu("wrap de lomo;",22000);
 	   	ProductoMenu productoMenu2 = new ProductoMenu("todoterreno;",25000);

 	   	pedido.agregarProducto(productoMenu);
 	   	pedido.agregarProducto(productoMenu2);
 	    
 	   	restaurante.cerrarYGuardarPedido();
 	   	assertNull(restaurante.getPedidoEnCurso(),"la prueba fallo: Existe un pedido en curso");
 	   	
 	   	assertTrue(archivoFactura.exists(), "La factura no se creó en la carpeta");
 	   	
 	   String contenido = Files.readString(archivoFactura.toPath());
 	   
 	    assertTrue(contenido.contains("wrap de lomo"), "La factura no contiene el producto 'wrap de lomo'");
 	    assertTrue(contenido.contains("todoterreno"), "La factura no contiene el producto 'todoterreno'");
 	}
       
    
    @TempDir
    Path carpetaTemporal; 
    
    private File archivoTemporal (Path dir, String nombre, String contenido) throws IOException {
        Path path = dir.resolve(nombre);
        Files.writeString(path, contenido);
        return path.toFile();
    }
    
    @Test
    @DisplayName("Probando que existan ingredientes repetidos")
    void testIngredienteRepetido() throws IngredienteRepetidoException, IOException {
    	
    	File menu = new File("data/menu.txt");
    	File combos = new File("data/combos.txt");
    	
    	File ingredientes = archivoTemporal(carpetaTemporal, "ingredientes.txt",
                "lechuga;1000\n" +
                "lechuga;1200\n"); 


        assertThrows(IngredienteRepetidoException.class,
                () -> restaurante.cargarInformacionRestaurante(ingredientes, menu, combos));
    	
    }
    
    @Test
    @DisplayName("Probando los productos faltantes")
    void testProductoFaltante() throws ProductoFaltanteException, IOException {
    	
    	File ingredientes = new File("data/ingredientes.txt");
    	
    	File menu = archivoTemporal(carpetaTemporal, "menu.txt",
                 "corral;28000\n");


        File combos = archivoTemporal(carpetaTemporal, "combos.txt",
                 "combo corral papas;10%;corral;papas\n");

        assertThrows(ProductoFaltanteException.class, () ->
                restaurante.cargarInformacionRestaurante(ingredientes, menu, combos));
    	
    }
    
    @Test
    @DisplayName("Probando los productos repetidos")
    void testProductoRepetido() throws ProductoRepetidoException, IOException {

    	File ingredientes = new File("data/ingredientes.txt");
        File combos = new File("data/combos.txt");
        
    	File menu = archivoTemporal(carpetaTemporal, "menu.txt",
                "gaseosa;5000\n" +
                "gaseosa;6000\n"); 

        assertThrows(ProductoRepetidoException.class, () ->
                restaurante.cargarInformacionRestaurante(ingredientes, menu, combos));
    }
    	
    

    @Test
    @DisplayName("Probando que no hay un pedido en curso y cerrarlo")
    void testNoHayPedidoEnCurso() throws NoHayPedidoEnCursoException {
    	
    	assertNull(restaurante.getPedidoEnCurso(),"la prueba fallo: Existe un pedido en curso");
    	
    	assertThrows(NoHayPedidoEnCursoException.class, () -> {
            restaurante.cerrarYGuardarPedido();
        }, "Lanza una excepcion porque no hay un pedido en curso");
    }
     
  
    
    @Test
    @DisplayName("Probando cargar el restaurante")
    void testCargarInfoRestaurante() throws NumberFormatException, HamburguesaException, IOException  {
    	
    	File ingredientes = new File("data/ingredientes.txt");
    	File menu = new File("data/menu.txt");
    	File combos = new File("data/combos.txt");
    	
    	restaurante.cargarInformacionRestaurante(ingredientes, menu, combos);
    	
    	assertEquals(15,restaurante.getIngredientes().size(),"No retorna todos los ingredientes");
    	assertEquals(22,restaurante.getMenuBase().size(),"No retorna todos los comprables del restaurante ");
    	assertEquals(4,restaurante.getMenuCombos().size(),"No retorna todos los combos");
    	
    }
}