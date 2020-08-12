package edu.uces.ar.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


@Entity
public class Report {

	private @Id @GeneratedValue Long id;
	private String processedDateTime;
	private float profit;
	private Integer totalCartsFailed;
	private Integer totalCartsProcessed;
	@ManyToMany
	@JoinTable(
	  name = "report_product", 
	  joinColumns = @JoinColumn(name = "report_id"), 
	  inverseJoinColumns = @JoinColumn(name = "product_id"))
	Set<Product> withoutStockProducts = new HashSet<Product>();

	
	public Report() {
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

	public Set<Product> getWithoutStockProducts()
	{
		return withoutStockProducts;
	}

	public void addWithoutStockProducts(Product pr)
	{
		withoutStockProducts.add(pr);
	}

	public void addWithoutStockProducts(Set<Product> withoutStockProducts2) {
		withoutStockProducts.addAll(withoutStockProducts2);
		
	}

	
}
