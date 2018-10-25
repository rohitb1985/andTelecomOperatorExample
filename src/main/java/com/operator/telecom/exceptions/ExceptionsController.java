package com.operator.telecom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

/**
 * This is the Exception Handler Controller.
 * Any Exception thrown by the application for the Rest Service operations will be handled by this controller and appropriate messages will be sent to the user
 * In the Response Entity currently returning a String only, however we can create a Error Details class and return multiple details like error message, timestamp, 
 * status code and any other relevant information
 * @author Rohit
 *
 */
@ControllerAdvice
@RestController
public class ExceptionsController {

	/**
	 * Method to handle the Customer Not Found Exception
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(CustomerNotFoundException.class)
	public final ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException ex, WebRequest request) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Method to handle the Phone Number Not Found Exception
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(PhoneNumberNotFoundException.class)
	public final ResponseEntity<String> handlePhoneNumberNotFoundException(PhoneNumberNotFoundException ex, WebRequest request) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
}
