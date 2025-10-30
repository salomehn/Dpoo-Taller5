package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import uniandes.dpoo.hamburguesas.excepciones.HamburguesaException;
import uniandes.dpoo.hamburguesas.excepciones.NoHayPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.excepciones.YaHayUnPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
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
    }
    
    

    @Test
    @DisplayName("Probando no hay pedido en curso")
    void testNoPedidoEnCurso() throws NoHayPedidoEnCursoException {
    	
    	assertNull(restaurante.getPedidoEnCurso(),"la prueba fallo: Existe un pedido en curso");
    	assertThrows(NoHayPedidoEnCursoException.class, () -> {
            restaurante.cerrarYGuardarPedido();
        }, "Lanza una excepcion porque no hay un pedido en curso");
    }
    
  
    
    @Test
    @DisplayName("Probando cargar el restaurante")
    void testCargarRestaurante() throws NumberFormatException, HamburguesaException, IOException  {
    	
    	File ingredientes = new File("data/ingredientes.txt");
    	File menu = new File("data/menu.txt");
    	File combos = new File("data/combos.txt");
    	
    	restaurante.cargarInformacionRestaurante(ingredientes, menu, combos);
    	
    	assertEquals(15,restaurante.getIngredientes().size(),"No retorna todos los ingredientes");
    	assertEquals(22,restaurante.getMenuBase().size(),"No retorna todos los comprables del restaurante ");
    	assertEquals(4,restaurante.getMenuCombos().size(),"No retorna todos los combos");
    	
    }
}