package com.operator.telecom.exceptions;

/**
 * Exception class to handle Phone Number Not Found Exception
 * @author Rohit
 *
 */
public class PhoneNumberNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public PhoneNumberNotFoundException(String exception){
		super(exception);
	}
}
