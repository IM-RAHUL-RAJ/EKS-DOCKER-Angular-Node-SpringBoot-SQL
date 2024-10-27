package com.capstone.exceptions;

public class IdentificationAlreadyExistsException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IdentificationAlreadyExistsException(String message) {
        super(message);
    }
}