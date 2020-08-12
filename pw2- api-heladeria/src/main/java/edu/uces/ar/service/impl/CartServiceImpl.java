package edu.uces.ar.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import edu.uces.ar.model.Cart;
import edu.uces.ar.model.CartProduct;
import edu.uces.ar.model.Product;
import edu.uces.ar.model.dto.CartDTO;
import edu.uces.ar.model.dto.CartProductDTO;
import edu.uces.ar.model.dto.ProductInCartDTO;
import edu.uces.ar.repository.CartProductRepository;
import edu.uces.ar.repository.CartRepository;
import edu.uces.ar.repository.ProductRepository;
import edu.uces.ar.service.CartService;
import edu.uces.ar.service.business.exception.CartDuplicatedException;
import edu.uces.ar.service.business.exception.CartNotFoundException;
import edu.uces.ar.service.business.exception.CartServiceException;
import edu.uces.ar.service.business.exception.ProductNotFoundException;
import edu.uces.ar.service.business.exception.ProductServiceException;

@Service
public class CartServiceImpl implements CartService{
	
	private final CartRepository cartRepo;
	private final CartProductRepository cartProductRepo;
	private final ProductRepository productRepo;
	
	public CartServiceImpl(CartRepository cartRepo, CartProductRepository cartProductRepo, ProductRepository productRepo) {
		super();
		this.cartRepo = cartRepo;
		this.cartProductRepo = cartProductRepo;
		this.productRepo = productRepo;

	}

	@Override
	public List<CartDTO> getAllCarts() {

		List<Cart> carts = cartRepo.findAll();
		List<CartDTO> dtos = new ArrayList<>(carts.size());
		

		for (int i = 0; i < carts.size(); i++) {
			CartDTO cartDTO = new CartDTO();
			BeanUtils.copyProperties(carts.get(i), cartDTO);
			cartDTO.setCreationDate(carts.get(i).getCreationDate());
			
			for(CartProduct cp : carts.get(i).getCartProducts())
			{
				ProductInCartDTO pcDTO = new ProductInCartDTO();
				pcDTO.setId(cp.getProduct().getId());
				pcDTO.setDescription(cp.getProduct().getDescription());
				pcDTO.setQuantity(cp.getQuantity());
				pcDTO.setUnitPrice(cp.getUnitPrice());
				cartDTO.getProductsInCart().add(pcDTO);

			}
			dtos.add(cartDTO);
		}
		
		return dtos;
	}
	
	@Override
	public List<ProductInCartDTO> getAllProductsForCart(long idCart) {

		Optional<Cart> cart = cartRepo.findById(idCart);
		
		if (!cart.isPresent()) {
			throw new CartNotFoundException("Invalid cart");
		}
		
		List<ProductInCartDTO> dtos = new ArrayList<>(cart.get().getCartProducts().size());
		for(CartProduct cp : cart.get().getCartProducts())
		{
			ProductInCartDTO pcDTO = new ProductInCartDTO();
			pcDTO.setId(cp.getProduct().getId());
			pcDTO.setDescription(cp.getProduct().getDescription());
			pcDTO.setQuantity(cp.getQuantity());
			pcDTO.setUnitPrice(cp.getUnitPrice());
				
			dtos.add(pcDTO);
				
		}

		return dtos;
	}
	
	@Override
	public CartDTO getCartById(Long id) {
		
		Optional<Cart> cart = cartRepo.findById(id);
		CartDTO cartDTO = new CartDTO();
		
		if (cart.isPresent()) {
			BeanUtils.copyProperties(cart.get(), cartDTO);
			
			for(CartProduct cp : cart.get().getCartProducts())
			{
				ProductInCartDTO pcDTO = new ProductInCartDTO();
				pcDTO.setId(cp.getProduct().getId());
				pcDTO.setDescription(cp.getProduct().getDescription());
				pcDTO.setQuantity(cp.getQuantity());
				pcDTO.setUnitPrice(cp.getUnitPrice());
				
				cartDTO.getProductsInCart().add(pcDTO);
				
			
			}
			
			
		} else {
			throw new CartNotFoundException("Invalid cart");
		}
		
		return cartDTO;

	}

	@Override
	public CartDTO getById(Long id) {
		
		Optional<Cart> cart = cartRepo.findById(id);
		CartDTO dto = new CartDTO();
		
		if (cart.isPresent()) {
			BeanUtils.copyProperties(cart.get(), dto);
		} else {
			throw new CartNotFoundException("Invalid cart");
		}
		
		return dto;
	}
	
	@Override
	public Long post(CartDTO cartDTO) {

		Cart cart = new Cart();
		
		if(getAllCarts().stream().anyMatch(cDTO -> cDTO.getEmail().equals(cartDTO.getEmail())))
		{
			throw new CartDuplicatedException("Duplicated cart");
		}
		if(cartDTO.getEmail() != null && cartDTO.getFullName() != null && !cartDTO.getFullName().isEmpty() && !cartDTO.getEmail().isEmpty())
		{
			BeanUtils.copyProperties(cartDTO, cart);
			cart = cartRepo.save(cart);
		}else {
			throw new CartServiceException("Invalid data to create Cart");
		}

		
		return cart.getId();
	}

	@Override
	public Long put(CartDTO cartDTO) {

		Cart cart = new Cart();
		BeanUtils.copyProperties(cartDTO, cart);
		cart = cartRepo.save(cart);
		
		return cart.getId();
	}

	@Override
	public void deleteById(Long id) {
		
		Optional<Cart> cart = cartRepo.findById(id);
		
		if (cart.isPresent()) {
			cartRepo.deleteById(id);
		} else {
			throw new CartNotFoundException("Cart " + id + " not found");
		}
		
		
	}

	@Override
	public void deleteProductFromCart(Long idCart, Long idProduct) {
		
		Optional<Cart> cart = cartRepo.findById(idCart);
		
		if (cart.isPresent()) {
			
			
			List<CartProduct> cps = cartProductRepo.findAll();
			Cart cart2 = cart.get();
			
			boolean productoExiste = false;
			
			for(CartProduct cp : cps)
			{
				if(cp.getCart().getId().equals(idCart) && cp.getProduct().getId().equals(idProduct))
				{
					productoExiste = true;
					cart2.removeProduct(idProduct, cp.getUnitPrice().floatValue()*cp.getQuantity().floatValue());
					cartRepo.save(cart2);
					cartProductRepo.delete(cp);
				}	
			}
			if(!productoExiste)
			{
				throw new ProductNotFoundException("Invalid product");
			}
		} else {
			throw new CartNotFoundException("Invalid cart");
		}
	}
	
	@Override
	public void addProduct(CartProductDTO cartProductDTO, Long idCart)
	{

		Long idProduct = cartProductDTO.getId();
		Long quantity = cartProductDTO.getQuantity();

		if (!(idProduct != null && idProduct > 0 && quantity != null && quantity > 0)) throw new CartServiceException("Invalid data to add product");

		Cart cart = new Cart();
		CartDTO cartDTO = getById(idCart);
		BeanUtils.copyProperties(cartDTO, cart);
		Optional<Product> product = productRepo.findById(idProduct);
			
		if (!product.isPresent()) throw new ProductNotFoundException("Invalid product");
		if (!(quantity <= product.get().getStock())) throw new ProductServiceException("Insufficient stock");

		List<CartProduct> cps = cartProductRepo.findAll();

					
		boolean productoExiste = false;
					
		for(CartProduct cp : cps)
		{
			if(cp.getCart().getId().equals(idCart) && cp.getProduct().getId().equals(idProduct))
			{
					cart.setTotal(cart.getTotal()-cp.getUnitPrice().floatValue()*cp.getQuantity().floatValue()+quantity.intValue()*product.get().getUnitPrice().floatValue());
							
					productoExiste = true;
					cartRepo.save(cart);
					cp.setQuantity(quantity.intValue());
					cp.setUnitPrice(product.get().getUnitPrice());
					cartProductRepo.save(cp);
			}
							
		}
		if(!productoExiste)
		{
			cart.addProduct(product.get(), quantity);
		
			cartProductRepo.save(new CartProduct(idCart, idProduct,product.get().getUnitPrice(), quantity));
			cartRepo.save(cart);	
						
						
		}
					
		
		
	}

	@Override
	public void checkoutCart(Long id) {
		
		
		Optional<Cart> cart = cartRepo.findById(id);
		
		if (cart.isPresent()) {
			
			Cart cart2 = cart.get();
			if(cart2.getStatus().equals("NEW"))
			{
				cart2.setStatus("READY");
				cart2.update();
				cartRepo.save(cart2);
			}
			else
			{
				throw new CartNotFoundException("Invalid cart status");
			}
			
		} else {
			throw new CartNotFoundException("Invalid cart");
		}
		
	}
	
}
