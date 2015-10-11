package com.beacon.model;

public class CustomerServiceQueue {
	private String tokenNumber;
	private String accountNumber;
	private String status;
	private String firstName;
	private String lastName;
	
	private byte[] imageBytes;
	
	public byte[] getImageBytes() {
		return imageBytes;
	}
	public void setImageBytes(byte[] imageBytes) {
		this.imageBytes = imageBytes;
	}
	
	public String getTokenNumber() {
		return tokenNumber;
	}
	public void setTokenNumber(String tokenNumber) {
		this.tokenNumber = tokenNumber;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
}
