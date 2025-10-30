package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ComboTest {
	
	private Combo combo;
	
	private double descuento;
	private String nombre; 
	private ArrayList<ProductoMenu> itemsCombo = new ArrayList<>();

	
	@BeforeEach
	void setup() throws Exception {
		
		ProductoMenu producto = new ProductoMenu("todoterreno;",25000);
		ProductoMenu producto2 = new ProductoMenu("papas grandes",6900);
		ProductoMenu producto3 = new ProductoMenu("gaseosa",5000);
		
		nombre = "combo todoterreno";
		descuento = 0.07;
		
		itemsCombo.add(producto);
		itemsCombo.add(producto2);
		itemsCombo.add(producto3);
		
		combo = new Combo(nombre, descuento,itemsCombo);
		
	}
	
	@AfterEach
	void teardown() 
	{	
	}
	
	@Test
	@DisplayName("Probando conseguir el nombre del combo")
	void testGetNombre() {
		assertEquals("combo todoterreno",combo.getNombre(),"El nombre no coincide");
	}
	
	@Test 
	@DisplayName("Probando generar el precio del combo")
	void testGetPrecio () {
		assertEquals (34317,combo.getPrecio(),"El precio no coincide");
		
		// 25000 + 6900 + +5000 = 36900 (Precio)
		// 36900 * 0.07 = 2583 (Descuento)
		// 36900 - 2583 = 34317 (precio final con descuento)
		
	}
	
	@Test 
	@DisplayName("Probando generar facturar combo")
	void testFacturaCombo() {
		String facturaEsperada =   "Combo " + "combo todoterreno" + "\n" + " Descuento: " + "0.07" + "\n" + "            " + "34317" + "\n" ;
		
		
		assertEquals(facturaEsperada,combo.generarTextoFactura(),"La prueba fallo: el texto no coincide");
		
	}
	
   
	
	
	

}
