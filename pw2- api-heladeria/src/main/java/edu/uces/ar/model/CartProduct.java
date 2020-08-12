package edu.uces.ar.model;

import java.math.BigDecimal;

import javax.persistence.*;

@Entity
public class CartProduct {

	@EmbeddedId
	CartProductKey id;

	@ManyToOne
	@MapsId("cart_id")
	@JoinColumn(name = "cart_id")
	Cart cart;
	
	@ManyToOne
	@MapsId("product_id")
	@JoinColumn(name = "product_id")
	Product product;
	
	int quantity;
	BigDecimal unitPrice;
	
	public CartProduct() {
		super();

	}

	public CartProduct(Long idCart, Long idProduct) {
		super();
		this.id = new CartProductKey(idCart, idProduct);

	}
	
	public CartProduct(Long idCart, Long idProduct, BigDecimal unitPrice, Long quantity) {
		super();
		this.id = new CartProductKey(idCart, idProduct);
		this.quantity = quantity.intValue();
		this.unitPrice = unitPrice;
	}

	public CartProductKey getId() {
		return id;
	}

	public void setId(CartProductKey id) {
		this.id = id;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	
}
