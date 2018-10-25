package com.operator.telecom.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.operator.telecom.exceptions.CustomerNotFoundException;
import com.operator.telecom.exceptions.PhoneNumberNotFoundException;
import com.operator.telecom.service.TelecomOperatorService;

/**
 * Rest Controller class to handle all the requests related to customer telephone numbers.
 * @author Rohit
 *
 */
@RestController
@RequestMapping("v1/telecom/org/api/")
public class TelecomOperatorController {

	@Autowired
	TelecomOperatorService telecomService;
	
	/**
	 * Service operation to return all the phone numbers
	 * @return
	 */
	@RequestMapping(value="allPhoneNumbers", method=RequestMethod.GET)
	public String getAllPhoneNumbers() throws PhoneNumberNotFoundException{
		return telecomService.getAllPhoneNumbers();
	}
	
	/**
	 * Service operation to return all the phone number details of a customer
	 * @param customerId
	 * @return
	 * @throws CustomerNotFoundException
	 */
	@RequestMapping(value="customerPhoneNumbers/{customerId}", method=RequestMethod.GET)
	public String getCustomerPhoneNumbers(@PathVariable("customerId") String customerId) throws CustomerNotFoundException{
		return telecomService.getCustomerPhoneNumbers(customerId);
	}
	
	/**
	 * Service operation to activate the phone number for a customer
	 * @param customerId
	 * @param phoneNumber
	 * @return
	 * @throws CustomerNotFoundException
	 * @throws PhoneNumberNotFoundException
	 */
	@RequestMapping(value="activateNumber/{customerId}/{phoneNumber}", method=RequestMethod.POST)
	public String activateNumber(@PathVariable("customerId") String customerId, @PathVariable("phoneNumber") String phoneNumber) 
			throws CustomerNotFoundException, PhoneNumberNotFoundException{
		return telecomService.activateNumber(customerId, phoneNumber);
	}
}
