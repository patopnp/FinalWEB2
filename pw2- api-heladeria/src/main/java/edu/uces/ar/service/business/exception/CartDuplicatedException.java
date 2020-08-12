package edu.uces.ar.service.business.exception;

public class CartDuplicatedException extends RuntimeException {
	
	private static final long serialVersionUID = -4961625479507744127L;

	public CartDuplicatedException(String message) {
        super(message);
    }

}
