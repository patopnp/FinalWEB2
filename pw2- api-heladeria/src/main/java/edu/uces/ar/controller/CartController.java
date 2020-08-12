package edu.uces.ar.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.uces.ar.model.dto.CartDTO;
import edu.uces.ar.model.dto.CartProductDTO;
import edu.uces.ar.model.dto.ProductInCartDTO;
import edu.uces.ar.service.CartService;

@RestController
@Validated
public class CartController {
	
	private final CartService cartService;
	public CartController(CartService cartService) {
		super();

		this.cartService = cartService;
	}

	@GetMapping(value = "/carts")
	public ResponseEntity<List<CartDTO>> getProducts() {
		return new ResponseEntity<>(cartService.getAllCarts(), HttpStatus.OK);
	}

	@GetMapping(value = "/carts/{id}")
	public ResponseEntity<CartDTO> getProduct(@PathVariable long id) {
		return new ResponseEntity<>(cartService.getCartById(id), HttpStatus.OK);
	}

	@GetMapping(value = "/carts/{id}/products")
	public ResponseEntity<List<ProductInCartDTO>> getProductsForCart(@PathVariable long id) {
		return new ResponseEntity<>(cartService.getAllProductsForCart(id), HttpStatus.OK);
	}
	
	@PostMapping(path = "/carts")
	public ResponseEntity<Object> postProduct(@Valid @RequestBody CartDTO productDTO) {
		Long id = cartService.post(productDTO);
		return new ResponseEntity<>("Cart successfully created. Id: " + id, HttpStatus.CREATED);
	}
	
	@PostMapping(path = "/carts/{id}/products")
	public ResponseEntity<Object> postCartProduct(@PathVariable long id,@Valid @RequestBody CartProductDTO cartProductDTO) {
		cartService.addProduct(cartProductDTO, id);
		return new ResponseEntity<>("Producto agregado al carro.", HttpStatus.OK);
	}
	
	@PostMapping(path = "/carts/{id}/checkout")
	public ResponseEntity<Object> postCartProduct(@PathVariable long id) {
		cartService.checkoutCart( id);
		return new ResponseEntity<>("Cart checked out", HttpStatus.OK);
	}
	
	@PutMapping(value = "/carts/{id}")
	public ResponseEntity<Object> putProduct(@PathVariable long id, @Valid @RequestBody CartDTO cartDTO) {
		cartDTO.setId(id);
		cartService.put(cartDTO);
		return new ResponseEntity<>("Cart successfully updated. Id: " + id, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/carts/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable long id) {
		cartService.deleteById(id);
		return new ResponseEntity<>("Cart deleted successfully. Id: " + id, HttpStatus.OK);
	}

	@DeleteMapping(value = "/carts/{cartId}/products/{productId}")
	public ResponseEntity<Object> deleteProduct(@PathVariable long cartId, @PathVariable long productId) {
		cartService.deleteProductFromCart(cartId, productId);
		return new ResponseEntity<>("Product deleted from cart successfully.", HttpStatus.OK);
	}
	
}
