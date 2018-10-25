package com.operator.telecom.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.operator.telecom.exceptions.CustomerNotFoundException;
import com.operator.telecom.exceptions.PhoneNumberNotFoundException;
import com.operator.telecom.model.Customer;
import com.operator.telecom.model.Phone;

/**
 * Service class to process the rest service operations
 * @author Rohit
 *
 */
@Component
public class TelecomOperatorService {

	Logger logger = LoggerFactory.getLogger(TelecomOperatorService.class);

	/**
	 * The static code to create a list of customers and associated phone numbers.
	 * This is written as there is no database to get this data.
	 */
	private static Map<Integer, Customer> customerList = new HashMap<Integer, Customer>();
	private static int phoneIdCounter = 0;
	static{
		Random randomPhone = new Random();
		String[] status = {"Active", "InActive"};
		for(int i = 0; i<5; i++){
			Customer customerObj = new Customer();
			Map<Long, Phone> phoneList = new HashMap<Long, Phone>();
			for(int j = 0; j<2; j++){
				Phone phoneObj = new Phone();
				phoneObj.setPhoneId(phoneIdCounter++);
				phoneObj.setPhoneNumber((long)(Math.random()*100000 + 7774500000L));
				phoneObj.setStatus(status[randomPhone.nextInt(status.length)]);
				phoneList.put(phoneObj.getPhoneNumber(), phoneObj);
			}
			customerObj.setCustomerId(i);
			customerObj.setCustomerName("Customer"+(i+1));
			customerObj.setPhoneList(phoneList);
			customerList.put(customerObj.getCustomerId(), customerObj);
		}
	}

	/**
	 * Service method to return all Phone Numbers.
	 * This method is currently returning all the data related to phone numbers for testing convenience.
	 * If required the method implementation can be modified to return the list of phone numbers only.
	 * @return
	 */
	public String getAllPhoneNumbers() throws PhoneNumberNotFoundException{
		logger.info("TelecomOperatorService.getAllPhoneNumbers begin");
		long startTime = System.currentTimeMillis();
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().disableHtmlEscaping()
				.setPrettyPrinting().create();
		List<Phone> phoneNumbersList = new ArrayList<Phone>();
		for(Map.Entry<Integer, Customer> customerEntry: customerList.entrySet()){
			Customer customer = customerEntry.getValue();
			Map<Long, Phone> phoneList = customer.getPhoneList();
			for(Map.Entry<Long, Phone> phoneEntry: phoneList.entrySet()){
				Phone phone = phoneEntry.getValue();
				phoneNumbersList.add(phone);
			}
		}
		if(phoneNumbersList.isEmpty()){
			throw new PhoneNumberNotFoundException("No Phone Numbers found");
		}
		logger.info("TelecomOperatorService.getAllPhoneNumbers end: "+ (System.currentTimeMillis()-startTime) + " ms");
		return gson.toJson(phoneNumbersList);
	}

	/**
	 * Service method to return customer phone number details.
	 * This method will return the customer details along with a map of associated phone numbers.
	 * @param customerId
	 * @return
	 * @throws CustomerNotFoundException
	 */
	public String getCustomerPhoneNumbers(String customerId) throws CustomerNotFoundException{
		logger.info("TelecomOperatorService.getCustomerPhoneNumbers begin");
		long startTime = System.currentTimeMillis();
		Customer customer = null;
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().disableHtmlEscaping()
				.setPrettyPrinting().create();

		customer = getCustomer(customerId);

		logger.info("TelecomOperatorService.getCustomerPhoneNumbers end: "+ (System.currentTimeMillis()-startTime) + " ms");
		return gson.toJson(customer);
	}

	/**
	 * Service method to activate a given phone number. If customer or number is not found or number is already active an exception is thrown and 
	 * a user friendly message is returned.
	 * @param customerId
	 * @param phoneNumber
	 * @return
	 * @throws CustomerNotFoundException
	 * @throws PhoneNumberNotFoundException
	 */
	public String activateNumber(String customerId, String phoneNumber) throws CustomerNotFoundException, PhoneNumberNotFoundException{
		logger.info("TelecomOperatorService.activateNumber begin");
		long startTime = System.currentTimeMillis();
		String message = null;
		
		Customer customer = getCustomer(customerId);
		Map<Long, Phone> phonesList = customer.getPhoneList();
		if(phonesList == null || phonesList.isEmpty()){
			throw new PhoneNumberNotFoundException("No Numbers found Associated with the customer");
		}else{
			message = activatePhoneNumber(phoneNumber, phonesList, customer);
		}
		logger.info("TelecomOperatorService.activateNumber end: "+ (System.currentTimeMillis()-startTime) + " ms");
		return message;
	}
	
	/**
	 * Method to return the Customer Details after validating the input. Throws exception when customer is not found.
	 * @param customerId
	 * @return
	 * @throws CustomerNotFoundException
	 */
	private Customer getCustomer(String customerId) throws CustomerNotFoundException{
		Customer customer = null;
		if(null == customerId || (null!=customerId && customerId.trim().equals(""))){
			throw new CustomerNotFoundException("Customer Id cannot be null or empty");
		}else if(!customerId.chars().allMatch(Character::isDigit)){
			throw new CustomerNotFoundException("Customer Id can only have numeric value");
		}else{
			customer = customerList.get(Integer.valueOf(customerId));
			if(null == customer){
				throw new CustomerNotFoundException("Customer not found");
			}
		}
		return customer;
	}

	/**
	 * Method to activate the phone number after input validation. Throws exception when number not found or number is already active
	 * @param phoneNumber
	 * @param phoneList
	 * @param customer
	 * @return
	 * @throws PhoneNumberNotFoundException
	 */
	private String activatePhoneNumber(String phoneNumber, Map<Long, Phone> phoneList, Customer customer) throws PhoneNumberNotFoundException{
		String message = null;

		if(null == phoneNumber || (null != phoneNumber && phoneNumber.trim().equals(""))){
			throw new PhoneNumberNotFoundException("Phone Number cannot be null or empty");
		}else if(!phoneNumber.chars().allMatch(Character::isDigit)){
			throw new PhoneNumberNotFoundException("Phone Number can only have numeric value");
		}else{
			Phone phone = phoneList.get(Long.parseLong(phoneNumber));
			if(null == phone){
				throw new PhoneNumberNotFoundException("Phone Number not associated with the customer");
			}else if(phone.getStatus().equalsIgnoreCase("Inactive")){
				phone.setStatus("Active");
				phoneList.put(Long.parseLong(phoneNumber), phone);
				customer.setPhoneList(phoneList);
				customerList.put(customer.getCustomerId(), customer);
				message = "Phone Number activated successfully";
			}else{
				throw new PhoneNumberNotFoundException("Phone Number is already active");
			}
		}
		return message;
	}
}
