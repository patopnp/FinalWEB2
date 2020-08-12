package edu.uces.ar.model.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CartDTO {

	private Long id;
	private String fullName;
	private String email;
	private String creationDate;
	private Set<ProductInCartDTO> products = new HashSet<ProductInCartDTO>();
	private float total;
	private String status;
	
	public CartDTO() {
		super();
		
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		creationDate = df.format(date);
	
		total = 0;
		status = "NEW";
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCreationDate()
	{
		return creationDate;
	}
	
	public void setCreationDate(String creationDate)
	{
		this.creationDate = creationDate;
	}	
	
	public float getTotal() {
		return total;
	}
	
	public void setTotal(float total) {
		this.total = total;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
	public Set<ProductInCartDTO> getProductsInCart() {
		return products;
	}
	public void setProductsInCart(Set<ProductInCartDTO> productsInCart) {
		this.products = productsInCart;
	}

}
