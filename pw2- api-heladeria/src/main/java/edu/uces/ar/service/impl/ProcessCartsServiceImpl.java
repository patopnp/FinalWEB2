package edu.uces.ar.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import edu.uces.ar.model.Cart;
import edu.uces.ar.model.CartProduct;
import edu.uces.ar.model.Product;
import edu.uces.ar.model.Report;
import edu.uces.ar.model.dto.CartDTO;
import edu.uces.ar.model.dto.ProductInCartDTO;
import edu.uces.ar.model.dto.ReportDTO;
import edu.uces.ar.repository.CartProductRepository;
import edu.uces.ar.repository.CartRepository;
import edu.uces.ar.repository.ProductRepository;
import edu.uces.ar.repository.ReportRepository;
import edu.uces.ar.service.ProcessCartsService;
import edu.uces.ar.service.business.exception.CartNotFoundException;

@Service
public class ProcessCartsServiceImpl implements ProcessCartsService{
	
	private final ProductRepository productRepo;
	private final CartRepository cartRepo;
	private final ReportRepository reportRepo;
	
	public ProcessCartsServiceImpl(CartRepository cartRepo, CartProductRepository cartProductRepo, ProductRepository productRepo, ReportRepository reportRepo) {
		super();
		this.productRepo = productRepo;
		this.cartRepo = cartRepo;
		this.reportRepo = reportRepo;
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
	public ReportDTO processCarts()
	{
		List<CartDTO> dtos = getAllCarts();
		
		
		//Ordeno los carritos por dia
		for(int i = 0; i < dtos.size(); i++)
		{
			for(int j = i; j < dtos.size(); j++)
			{
				if(dtos.get(j).getCreationDate().compareTo(dtos.get(i).getCreationDate()) < 0)
				{
					
					CartDTO auxiliar = dtos.get(i);
					dtos.set(i, dtos.get(j));
					dtos.set(j, auxiliar);
					
				}
			}
		}
		

		ReportDTO rdto = new ReportDTO();
		rdto.setTotalCartsFailed(0);
		rdto.setTotalCartsProcessed(0);
		
		//el procesamiento en paralelo se hace para cada carrito
		for(int i = 0; i < dtos.size(); i++)
		{

			if(dtos.get(i).getStatus().equals("READY"))
			{
				
				if(checkProcessing(dtos.get(i), rdto)) {
					
					processCart(dtos.get(i));
					rdto.setTotalCartsProcessed(rdto.getTotalCartsProcessed()+1);
					rdto.setProfit(rdto.getProfit()+dtos.get(i).getTotal());
					

					
					changeToProcessed(dtos.get(i).getId());
				}
				else
				{
					changeToFailed(dtos.get(i).getId());
					rdto.setTotalCartsFailed(rdto.getTotalCartsFailed()+1);
				}
				
				
			}
			

			
		}
		
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String processedDateTime = df.format(date);
		rdto.setProcessedDateTime(processedDateTime);
		
		Report report = new Report();
		BeanUtils.copyProperties(rdto, report);
		
		
		report.addWithoutStockProducts(rdto.getWithoutStockProducts());
		report = reportRepo.save(report);
		rdto.setId(report.getId());
		return rdto;
		
	}
	public void processCart(CartDTO cart)
	{
			Set<ProductInCartDTO> setOfPICDTO = cart.getProductsInCart();
			setOfPICDTO.parallelStream().forEach(e -> discountFromStock(e));
	}
	public void discountFromStock(ProductInCartDTO pdto)
	{
		
		List<Product> products = productRepo.findAll();
		Product product = products.stream().filter(cp->cp.getId() == pdto.getId()).findFirst().get();
		if(product.getStock() >= pdto.getQuantity())
		{
			product.setStock(product.getStock() - pdto.getQuantity());
			product = productRepo.save(product);
		}
		
	}
	
	public boolean checkProcessing(CartDTO cart, ReportDTO rdto)
	{

		Set<ProductInCartDTO> setOfPICDTO = cart.getProductsInCart();
		boolean valid = !(setOfPICDTO.parallelStream().map(pdto -> checkForStock(pdto, rdto)).anyMatch(e -> e==false));
		return valid;
		
	}
	public boolean checkForStock(ProductInCartDTO pdto, ReportDTO rdto)
	{
		
		List<Product> products = productRepo.findAll();
		Product product = products.stream().filter(cp->cp.getId() == pdto.getId()).findFirst().get();
		if((product.getStock() < pdto.getQuantity()))
		{
			rdto.addWithoutStockProducts(product);
			return false;
		}
		return true;
	}
	
	

	public void changeToProcessed(Long id)
	{
		Optional<Cart> cart = cartRepo.findById(id);
		
		if (cart.isPresent()) {
			
			Cart cart2 = cart.get();
			cart2.setStatus("PROCESSED");
			cartRepo.save(cart2);

		} else {
			throw new CartNotFoundException("Invalid cart");
		}
	}

	public void changeToFailed(Long id)
	{
		Optional<Cart> cart = cartRepo.findById(id);
		
		if (cart.isPresent()) {
			Cart cart2 = cart.get();
			cart2.setStatus("FAILED");
			cartRepo.save(cart2);
		} else {
			throw new CartNotFoundException("Invalid cart");
		}
	}

	
	@Override
	public List<ReportDTO> getAllReports() {

		List<Report> reports = reportRepo.findAll();
		List<ReportDTO> dtos = new ArrayList<>(reports.size());
		
		//mapeo de listas
		for (int i = 0; i < reports.size(); i++) {
			ReportDTO reportDTO = new ReportDTO();
			BeanUtils.copyProperties(reports.get(i), reportDTO);
			System.out.println("SIZE= "+reports.get(i).getWithoutStockProducts().size());
			dtos.add(reportDTO);
		}
		
		return dtos;
	}

	@Override
	public List<ReportDTO> getReports(String from, String to) {

		List<Report> reports = reportRepo.findAll();
		List<ReportDTO> dtos = new ArrayList<>(reports.size());
		
		//mapeo de listas
		for (int i = 0; i < reports.size(); i++) {
			
			Report rep = reports.get(i);
			String sDate = rep.getProcessedDateTime();
			Date date;
			Date dateFrom;
			Date dateTo;
			try {
				date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(sDate);
				dateFrom = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(from); 
				dateTo = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(to);
				
		        if (date.compareTo(dateFrom) > 0 && date.compareTo(dateTo) < 0) { 
		  
					ReportDTO reportDTO = new ReportDTO();
					BeanUtils.copyProperties(rep, reportDTO);
					dtos.add(reportDTO);
		        } 

			} catch (ParseException e) {
				e.printStackTrace();
			} 

		}
		
		return dtos;
	}

}
