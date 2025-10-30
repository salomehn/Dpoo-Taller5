package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoMenuTest {

    private ProductoMenu producto;

    @BeforeEach
    void setUp() throws Exception
    {
    	producto = new ProductoMenu("gaseosa",5000);
    }
    
    @AfterEach
    void tearDown( ) throws Exception
    {
    }

    @Test
    @DisplayName("Probando getNombre...")
    void testGetNombre() {
        assertEquals("gaseosa", producto.getNombre(),"No retorna el nombre correcto" );
    }

    @Test
    @DisplayName("Probando getPrecio...")
    void testGetPrecio() {
        assertEquals(5000, producto.getPrecio(),"No retorna el preccio correcto");
    }

    @Test
    @DisplayName("Generando el texto de la factura (probando)")
    void testGenerarTextoFactura() {
        String textoFactura = producto.generarTextoFactura();
        assertEquals("gaseosa\n            5000\n", textoFactura, "La prueba fallo: el texto no coincide");
	}
}
