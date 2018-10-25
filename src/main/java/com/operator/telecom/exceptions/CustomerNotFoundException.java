package com.operator.telecom.exceptions;

/**
 * Exception class to handle Customer Not Found Exception
 * @author Rohit
 *
 */
public class CustomerNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CustomerNotFoundException(String exception) {
		super(exception);
	}

}
