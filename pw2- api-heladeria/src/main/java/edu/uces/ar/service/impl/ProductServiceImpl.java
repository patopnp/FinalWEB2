package edu.uces.ar.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import edu.uces.ar.model.Product;
import edu.uces.ar.model.dto.ProductDTO;
import edu.uces.ar.repository.ProductRepository;
import edu.uces.ar.service.ProductService;
import edu.uces.ar.service.business.exception.ProductNotFoundException;

@Service
public class ProductServiceImpl implements ProductService{
	
	private final ProductRepository productRepo;
	
	public ProductServiceImpl(ProductRepository productRepo) {
		super();
		this.productRepo = productRepo;
	}

	@Override
	public List<ProductDTO> getAll() {

		List<Product> products = productRepo.findAll();
		List<ProductDTO> dtos = new ArrayList<>(products.size());
		
		//mapeo de listas
		for (int i = 0; i < products.size(); i++) {
			ProductDTO productDTO = new ProductDTO();
			BeanUtils.copyProperties(products.get(i), productDTO);
			dtos.add(productDTO);
		}
		
		return dtos;
	}

	@Override
	public ProductDTO getById(Long id) {
		
		Optional<Product> product = productRepo.findById(id);
		ProductDTO dto = new ProductDTO();
		
		if (product.isPresent()) {
			BeanUtils.copyProperties(product.get(), dto);
		} else {
			throw new ProductNotFoundException("Product " + id + " not found");
		}
		
		return dto;
	}
	
	@Override
	public Long post(ProductDTO productDTO) {

		Product product = new Product();
		BeanUtils.copyProperties(productDTO, product);
		product = productRepo.save(product);
		
		return product.getId();
	}

	@Override
	public Long put(ProductDTO productDTO) {
		Product product = new Product();
		long id = productDTO.getId();

		if (productRepo.findById(id).isPresent()) {
			
			Optional<Product> productCopiar = productRepo.findById(id);
			
			if(productDTO.getStock() == null) productDTO.setStock(productCopiar.get().getStock());
			
			BeanUtils.copyProperties(productDTO, product);
			
			
			product = productRepo.save(product);
		} else {
			throw new ProductNotFoundException("Product " + id + " not found");
		}
		return product.getId();
	}

	@Override
	public void deleteById(Long id) {
		productRepo.deleteById(id);
	}

}
