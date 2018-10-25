package com.operator.telecom.model;

/**
 * Bean class for all the phone number related information
 * @author Rohit
 *
 */
public class Phone {

	private int phoneId;
	private long phoneNumber;
	private String status;
	
	public int getPhoneId() {
		return phoneId;
	}
	public void setPhoneId(int phoneId) {
		this.phoneId = phoneId;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
