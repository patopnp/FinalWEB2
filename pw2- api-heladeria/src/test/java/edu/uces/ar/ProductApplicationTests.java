package edu.uces.ar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import edu.uces.ar.service.business.exception.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.uces.ar.model.Product;
import edu.uces.ar.model.dto.ProductDTO;
import edu.uces.ar.repository.CartRepository;
import edu.uces.ar.repository.ProductRepository;
import edu.uces.ar.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductApplicationTests {

	@Autowired
	private CartRepository repo;
	@Autowired
	private ProductRepository prod;
	@Autowired
	private ProductService prodService;
	public ProductApplicationTests() {
	}
	 @Before
	 public void seEjecutaAntesDeCadaTest() {
	 }
	 @BeforeClass
	 public static void seEjecutaUnaSolaVezAlPrincipio() {
	 }



	 

	 @Test(expected = ProductNotFoundException.class)
	 public void testProductServiceFromId() {
	 prodService.getById(5000L);
	 }

	 @Test
	 public void testProductCreation() {

	 ProductDTO product = new ProductDTO();
	 product.setDescription("Poleta de futbol");
	 product.setUnitPrice(BigDecimal.TEN);
	 product.setStock(100);

	 long prodId = prodService.post(product);
	assertTrue(prodId > 0);
	 }

	 @Test
	 public void testProductPrice() {
	 ProductDTO productGuardado =
	prodService.getAll().get(0);

	assertTrue(productGuardado.getUnitPrice().compareTo(BigDecimal.ZERO) >
	0);
	 }

	 @Test
	 public void testProductById() {
	 ProductDTO productGuardado =
	prodService.getAll().get(0);
	 ProductDTO productGuardado2 =
	prodService.getById(productGuardado.getId());
	 assertTrue(productGuardado2.getId() > 0);
	 }

	 @Test
	 public void testProductStock() {
	 ProductDTO productGuardado =
	prodService.getAll().get(0);
	assertTrue(productGuardado.getStock() > 0);
	 }


	/* @Test
	 public void testUnitario() {
	
	//este test funciona solo si la aplicacion se levanta una vez en la base de datos porque sino llena mas filas en las tablas de las que esta previsto 	 
	 Product producto = new Product();
	 producto.setDescription("Raqueta de Tenis");
	 producto.setStock(30);
	 producto.setUnitPrice(new BigDecimal(4500));
	 Product productoGrabado = prod.save(producto);
	
	 assertEquals(prodService.getAll().size(), 1);
	
	 assertTrue(!prodService.getAll().isEmpty());
	
	 assertFalse(prodService.getAll().isEmpty());
	 
	
	assertNotNull(prodService.getById(productoGrabado.getId(
	)));
	
	 assertNull(repo.findById(222L).orElse(null));
	
	 }*/
	
	 @Test(expected = ProductNotFoundException.class)
	 public void testUnitarioConException() {
	 prodService.getById(1111L);
	 }

	 
	 
	 @After
	 public void seEjecutaDespuesDeCadaTest() {
	 }
	 @AfterClass
	 public static void seEjecutaUnaSolaVezAlFinal() {
	 }
	}