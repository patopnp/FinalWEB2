package edu.uces.ar.model.dto;

public class CartProductDTO {

	private Long id;
	private Long quantity;

	public CartProductDTO() {
		super();
	}
	
	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

}

