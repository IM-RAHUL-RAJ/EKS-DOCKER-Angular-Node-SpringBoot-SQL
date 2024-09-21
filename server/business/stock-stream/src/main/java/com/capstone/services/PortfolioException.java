package com.capstone.services;

public class PortfolioException extends Exception {
	   public PortfolioException(String message) {
	        super(message);
	    }

	    public static class PortfolioAlreadyExistsException extends PortfolioException {
	        public PortfolioAlreadyExistsException(String message) {
	            super(message);
	        }
	    }

	    public static class PortfolioValidationException extends PortfolioException {
	        public PortfolioValidationException(String message) {
	            super(message);
	        }
	    }
	}