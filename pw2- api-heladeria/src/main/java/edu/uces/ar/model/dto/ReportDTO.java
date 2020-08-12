package edu.uces.ar.model.dto;

import java.util.HashSet;
import java.util.Set;

import edu.uces.ar.model.Product;

public class ReportDTO {

	private Long id;
	private String processedDateTime;
	private float profit;
	private Integer totalCartsFailed;
	private Integer totalCartsProcessed;
	private Set<Product> withoutStockProducts = new HashSet<Product>();
	
	public ReportDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProcessedDateTime() {
		return processedDateTime;
	}

	public void setProcessedDateTime(String processedDateTime) {
		this.processedDateTime = processedDateTime;
	}

	public float getProfit() {
		return profit;
	}

	public void setProfit(float profit) {
		this.profit = profit;
	}

	public Integer getTotalCartsFailed() {
		return totalCartsFailed;
	}

	public void setTotalCartsFailed(Integer totalCartsFailed) {
		this.totalCartsFailed = totalCartsFailed;
	}

	public Integer getTotalCartsProcessed() {
		return totalCartsProcessed;
	}

	public void setTotalCartsProcessed(Integer totalCartsProcessed) {
		this.totalCartsProcessed = totalCartsProcessed;
	}

	public Set<Product> getWithoutStockProducts() {
		return withoutStockProducts;
	}

	public void setWithoutStockProducts(Set<Product> withoutStockProducts) {
		this.withoutStockProducts = withoutStockProducts;
	}

	public void addWithoutStockProducts(Product withoutStockProduct) {
		this.withoutStockProducts.add(withoutStockProduct);
	}
	
}
