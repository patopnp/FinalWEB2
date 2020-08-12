package edu.uces.ar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
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
import edu.uces.ar.model.dto.CartDTO;
import edu.uces.ar.model.dto.ProductDTO;
import edu.uces.ar.repository.CartRepository;
import edu.uces.ar.repository.ProductRepository;
import edu.uces.ar.service.CartService;
import edu.uces.ar.service.ProductService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class Parcial2Tests {
	
	List<CartDTO> todosLosCarts;
	List<ProductDTO> todosLosProductos;
	
	@Autowired
	private CartRepository repo;
	@Autowired
	private CartService cartService;
	@Autowired
	private ProductRepository prod;
	@Autowired
	private ProductService prodService;
	public Parcial2Tests() {
	}
	
	 //0 - Obtener todos los productos y carritos
	 @Before
	 public void seEjecutaAntesDeCadaTest() {
		 
		 todosLosCarts = cartService.getAllCarts();
		 todosLosProductos = prodService.getAll();
		 
	 }
	 @BeforeClass
	 public static void seEjecutaUnaSolaVezAlPrincipio() {
 
	 }
	 
	 
	//1- Esperar que solo exista un carrito
	 @Test
	 public void testCartsSize() {
		
					
		assertTrue(todosLosCarts.size() == 1);
	 }
	 
	 
	 //2- Esperar que solo exista un carrito en estado PROCESSED
	 @Test
	 public void testProcessed() {
		 

			int cantProcessed = 0;
			for(CartDTO cdto : todosLosCarts)
			{
				if(cdto.getStatus().equals("PROCESSED")) cantProcessed++;

			}
			
			assertTrue(cantProcessed == 1);
	 }
	 
	 
	 //3- Esperar que no exista ningun carrito en estado FAILED
	 @Test
	 public void testFailed() {


			int cantFailed = 0;
			for(CartDTO cdto : todosLosCarts)
			{
				if(cdto.getStatus().equals("FAILED")) cantFailed++;
			}
			assertTrue(cantFailed == 0);
	 }
	 
	 
	 //4- Esperar que solo existan 3 productos
	 @Test
	 public void testProductsSize() {

			assertTrue(prodService.getAll().size() == 3);
	 }
	 
	 //5- Esperar que solo un producto este sin stock
	 @Test
	 public void testWithoutStock() {


			int cantProdSinStock = 0;
			
			for(ProductDTO pdto : todosLosProductos)
			{
				if(pdto.getStock() == 0)
				{
					cantProdSinStock++;
				}
			}
			
			assertTrue(cantProdSinStock == 1);
	 }
	 
	 /*
	  corregido de 2do Parcial
	 @Test
	 public void testAllCarts() {
		 //0- Obtener todos los carritos existentes y todos los productos existentes
		List<CartDTO> todosLosCarts =
		cartService.getAllCarts();
		List<ProductDTO> todosLosProductos = prodService.getAll();
		
		//1- Esperar que solo exista un carrito
		assertTrue(todosLosCarts.size() == 1);
		
		int cantProcessed = 0;

		int cantFailed = 0;

		for(CartDTO cdto : todosLosCarts)
		{
			if(cdto.getStatus().equals("PROCESSED")) cantProcessed++;

			if(cdto.getStatus().equals("FAILED")) cantFailed++;

		}
		//2- Esperar que solo exista un carrito en estado PROCESSED
		assertTrue(cantProcessed == 1);

		//3- Esperar que no exista ningun carrito en estado FAILED
		assertTrue(cantFailed == 0);

		//4- Esperar que solo existan 3 productos
		assertTrue(prodService.getAll().size() == 3);
		
		
		
		int cantProdSinStock = 0;
		
		for(ProductDTO pdto : todosLosProductos)
		{
			if(pdto.getStock() == 0)
			{
				cantProdSinStock++;
			}
		}
		
		//5- Esperar que solo un producto este sin stock
		assertTrue(cantProdSinStock == 1);
	 }*/

	 
	 
	 @After
	 public void seEjecutaDespuesDeCadaTest() {
	 }
	 @AfterClass
	 public static void seEjecutaUnaSolaVezAlFinal() {
	 }
	}