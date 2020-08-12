package edu.uces.ar.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Cart {

	private @Id @GeneratedValue Long id;
	private String fullName;
	private String email;
	private String creationDate;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "cart")
	private Set<CartProduct> cartProducts = new HashSet<CartProduct>();
	private float total;
	private String status;
	
	public Cart() {
		super();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getCreationDate()
	{
		return creationDate;
	}
	
	public void setCreationDate(String creationDate)
	{
		this.creationDate = creationDate;
	}	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Set<CartProduct> getCartProducts()
	{
		return cartProducts;
	}

	public void addProduct(Product pr,Long quantity)
	{
		total = (float)(total+pr.getUnitPrice().floatValue()*quantity.floatValue());
		CartProduct cp = new CartProduct();
		cp.setCart(this);
		cp.setProduct(pr);
		cartProducts.add(cp);
	}
	public void removeProduct(Long idProduct, float price)
	{
		cartProducts.remove(cartProducts.stream().filter(cp->cp.getProduct().getId().equals(idProduct)).findFirst().get());
		total = (float)(total-price);	
	}

	public float getTotal() {
		return total;
	}
	
	public void setTotal(float total) {
		this.total = total;
	}
	
	public void update()
	{
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		creationDate = df.format(date);
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	

}
