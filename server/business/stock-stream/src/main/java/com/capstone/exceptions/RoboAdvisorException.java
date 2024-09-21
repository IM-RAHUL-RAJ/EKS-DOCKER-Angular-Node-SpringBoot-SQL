package com.capstone.exceptions;

public class RoboAdvisorException extends RuntimeException {

	public RoboAdvisorException(String message) {
		super(message);
	}

	public static class RoboAdvisorValidationException extends RoboAdvisorException {
		public RoboAdvisorValidationException(String message) {
			super(message);
		}
	}
}
