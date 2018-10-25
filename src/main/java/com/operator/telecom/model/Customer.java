package com.operator.telecom.model;

import java.util.Map;

/**
 * Bean class for Customer information
 * @author Rohit
 *
 */
public class Customer {

	private int customerId;
	private String customerName;
	private Map<Long, Phone> phoneList;
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Map<Long, Phone> getPhoneList() {
		return phoneList;
	}
	public void setPhoneList(Map<Long, Phone> phoneList) {
		this.phoneList = phoneList;
	}
	
}
