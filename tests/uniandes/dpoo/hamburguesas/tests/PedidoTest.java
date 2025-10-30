package uniandes.dpoo.hamburguesas.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.Producto;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class PedidoTest {

	private Combo combo;
	private ProductoAjustado productoAjustado;
	private Pedido pedido;
	
	
	private ProductoMenu productoMenu;
	private ProductoMenu productoMenu2; 
	
	private ProductoMenu producto;
	private ProductoMenu producto2;
	private ProductoMenu producto3;
	
	private Ingrediente pepinillos;
	private Ingrediente lechuga;
	
	private ArrayList<Producto> productos;
	private ArrayList<ProductoMenu> itemsCombo = new ArrayList<>();
	
	@BeforeEach
	void setup() throws Exception {
		
		pedido.numeroPedidos = 0;
		
		productoMenu= new ProductoMenu("todoterreno;",25000);
		productoMenu2 = new ProductoMenu("wrap de lomo;",22000);
		
		productoAjustado = new ProductoAjustado(productoMenu);
		pepinillos = new Ingrediente("pepinillos",2500);
		lechuga = new Ingrediente ("lechuga",1000);
		productoAjustado.agregarIngrediente(pepinillos);
		productoAjustado.eliminarIngrediente(lechuga);
		
		producto = new ProductoMenu("corral queso",16000);
		producto2 = new ProductoMenu("papas medianas",5500);
		producto3 = new ProductoMenu("gaseosa",5000);
		
		itemsCombo.add(producto);
		itemsCombo.add(producto2);
		itemsCombo.add(producto3);
		combo = new Combo("corral queso",0.1,itemsCombo);	
		
		pedido = new Pedido("Mariana","Calle 128 #75-67");
		pedido.agregarProducto(productoAjustado);
		pedido.agregarProducto(combo);
		pedido.agregarProducto(productoMenu2);
		

	}
	
	@AfterEach 
	void teardown() throws Exception {
		
	}
	
	
	@Test
	@DisplayName("Probando conseguir el id del pedido")
	void testGetIdPedido( ) {
		
		assertEquals(1,pedido.getIdPedido(),"El id no coincide");
	}
	
	
	@Test
	@DisplayName ("Probando conseguir el nombre del cliente")
	void testGetgetNombreCliente() {
		
		assertEquals ("Mariana",pedido.getNombreCliente(),"El nombre del cliente no coincide");
		
	}
	
	@Test 
	@DisplayName("Probando asi se agregaron los productos")
	void testAgregarProducto() {
		 productos = pedido.getProductos();
		 int cantidadProductos = productos.size();
		 
		 assertEquals (3,cantidadProductos,"Los productos añadidos no coinciden");
		 
	}
	
	@Test
	@DisplayName("Probando el precio total del pedido")
	void testGetPrecioTotalPedido( ) {
		int precioIva = (int) ((25000 + 2500 + 22000 + combo.getPrecio()) * (0.19));
		int precioNeto = 25000 + 2500 + 22000 + combo.getPrecio();
		
		int precioTotal = precioIva + precioNeto;
		
		assertEquals (precioTotal,pedido.getPrecioTotalPedido(),"El precio total no coincide");
		
	}
	
	@Test
	@DisplayName("Probando el precio neto del pedido")
	void testGetPrecioNetoPedido( ) {
		
		int precioNetoEsperado = 25000 + 2500 + 22000 + combo.getPrecio();
		assertEquals (precioNetoEsperado, pedido.getPrecioNetoPedido(),"El precio neto no coincide");
		
	}
	
	
	@Test
	@DisplayName("Probando conseguir el iva pedido")
	void testGetPrecioIVAPedido() {
		int precioIvaEsperado = (int) ((25000 + 2500 + 22000 + combo.getPrecio()) * (0.19));
		
		assertEquals (precioIvaEsperado, pedido.getPrecioIVAPedido(),"El IVA no coincide");
	}
	
	@Test
	void testGenerarTextofactura() {
		String factura = pedido.generarTextoFactura();
		assertTrue(factura.contains(pedido.getNombreCliente()));
		assertTrue(factura.contains(String.valueOf(pedido.getPrecioTotalPedido())));
		for(Producto producto : pedido.getProductos()) {
			assertTrue(factura.contains(producto.generarTextoFactura()));
		}
		
	}
	
	@Test
	@DisplayName("Probando conseguir la factura del pedido")
	void testGuardarFactura() {

	    File carpeta = new File("data/facturas");
	    File archivo = new File("data/facturas/factura.txt");
	    
	    if (!carpeta.exists()) {
	        carpeta.mkdirs();
	    }
	    
	    try {
	        // Borra el archivo antiguo
	        Files.deleteIfExists(archivo.toPath());
	        
	        pedido.guardarFactura(archivo);
	        String linea = Files.readString(archivo.toPath());
	        for (Producto producto : pedido.getProductos()) {
	            assertTrue(linea.contains(producto.generarTextoFactura()));
	        }

	    } catch (IOException e) {
	        System.err.println("No se encontró el archivo: " + archivo.getAbsolutePath());
	    }
	}	

}
