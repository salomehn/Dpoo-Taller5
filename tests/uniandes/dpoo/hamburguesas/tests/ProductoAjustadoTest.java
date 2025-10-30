package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import uniandes.dpoo.hamburguesas.mundo.*;

public class ProductoAjustadoTest {

    private ProductoMenu producto;
    private ProductoAjustado  productoAjustado;
    
    private Ingrediente queso;
    private Ingrediente pepinillos;
    private Ingrediente cebolla;
    private Ingrediente lechuga;

    @BeforeEach
    void setUp() throws Exception {
    	
    	producto = new ProductoMenu("corralita", 13000);
    	productoAjustado = new ProductoAjustado (producto);
    	
        queso = new Ingrediente("queso americano", 2500);
        pepinillos = new Ingrediente("pepinillos",2500);
        cebolla = new Ingrediente("cebolla", 1000);
        lechuga = new Ingrediente ("lechuga",1000);
        
    }
    
    @AfterEach
    void tearDown() throws Exception
    {
    }
    
    @Test 
    @DisplayName("Probando getNombre() de producto ajustado")
    void testGetNombre () {
    	assertEquals("corralita",productoAjustado.getNombre(),"No retorna el nombre correcto");
    }
    
    
    @Test
    @DisplayName("Probando getPrecio() sin adiciones") 
    
    void testGetPrecio() {
    	
    	
    	assertEquals(13000,productoAjustado.getPrecio(),"No retorna el precio correcto");
    }    
    
    @Test
    @DisplayName("Probando getPrecio() con adiciones") 
    
    void testGetPrecioAdiciones() {
    	
    	productoAjustado.agregarIngrediente(queso);
    	productoAjustado.agregarIngrediente(pepinillos);
    	
    	assertEquals(18000,productoAjustado.getPrecio(),"No retorna el precio correcto");
    }



    @Test
    @DisplayName("Probando la generacion de facturas de producto ajustado")
    void testGenerarTextoFactura() {
    	
    	productoAjustado.agregarIngrediente(queso);
    	productoAjustado.agregarIngrediente(pepinillos);
    	productoAjustado.eliminarIngrediente(cebolla);
    	productoAjustado.eliminarIngrediente(lechuga);
    	
    	 String facturaEsperada = "corralita                13000    +queso americano                2500    +pepinillos                2500    -cebolla    -lechuga            18000\n"
    	 		+ "";

        assertEquals(facturaEsperada, productoAjustado.generarTextoFactura(),"La prueba fallo: el texto no coincide");
        
      
    }
}
