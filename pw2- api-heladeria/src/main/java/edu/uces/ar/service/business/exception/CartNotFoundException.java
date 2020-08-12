package edu.uces.ar.service.business.exception;

public class CartNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -4961625479507744127L;

	public CartNotFoundException(String message) {
        super(message);
    }

}
