package edu.uces.ar.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import edu.uces.ar.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

	
}
