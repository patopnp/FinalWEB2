package edu.uces.ar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.uces.ar.model.dto.CartDTO;
import edu.uces.ar.model.dto.CartProductDTO;
import edu.uces.ar.model.dto.ProductInCartDTO;

@Service
public interface CartService {

	List<CartDTO> getAllCarts();
	
	List<ProductInCartDTO> getAllProductsForCart(long idCart);
	
	CartDTO getById(Long id);
	
	CartDTO getCartById(Long id);
	
	Long post(CartDTO cart);
	
	Long put(CartDTO cart);
	
	void addProduct(CartProductDTO cartProductDTO, Long idCart);
	
	void checkoutCart(Long id);
	
	void deleteById(Long id);
	
	void deleteProductFromCart(Long idCart, Long idProduct);
	
}
