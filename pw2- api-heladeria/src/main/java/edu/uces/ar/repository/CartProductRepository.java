package edu.uces.ar.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import edu.uces.ar.model.CartProduct;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

	
}
