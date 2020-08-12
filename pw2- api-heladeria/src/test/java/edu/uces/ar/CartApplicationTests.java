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
public class CartApplicationTests {

	@Autowired
	private CartRepository repo;
	@Autowired
	private CartService cartService;
	@Autowired
	private ProductRepository prod;
	@Autowired
	private ProductService prodService;
	public CartApplicationTests() {
	}
	 @Before
	 public void seEjecutaAntesDeCadaTest() {
	 }
	 @BeforeClass
	 public static void seEjecutaUnaSolaVezAlPrincipio() {
	 }


	 @Test
	 public void testAllCarts() {
		List<CartDTO> todosLosCarts =
		cartService.getAllCarts();
		assertTrue(todosLosCarts.size() == 1);
		
		int cantProcessed = 0;
		//int cantNew = 0;
		int cantFailed = 0;
		//int cantReady = 0;
		for(CartDTO cdto : todosLosCarts)
		{
			if(cdto.getStatus().equals("PROCESSED")) cantProcessed++;
		//	if(cdto.getStatus().equals("NEW")) cantNew++;
			if(cdto.getStatus().equals("FAILED")) cantFailed++;
		//	if(cdto.getStatus().equals("READY")) cantReady++;
		}
		assertTrue(cantProcessed == 1);
		//assertTrue(cantNew == 0);
		assertTrue(cantFailed == 0);
		//assertTrue(cantReady == 0);
		assertTrue(prodService.getAll().size() == 3);
		
		List<ProductDTO> todosLosProductos = prodService.getAll();
		
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

	 
	 
	 @After
	 public void seEjecutaDespuesDeCadaTest() {
	 }
	 @AfterClass
	 public static void seEjecutaUnaSolaVezAlFinal() {
	 }
	}