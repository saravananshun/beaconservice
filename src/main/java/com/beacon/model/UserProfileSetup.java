package com.beacon.model;

import org.springframework.web.multipart.MultipartFile;

public class UserProfileSetup {
	private String imeiNumber;
	private String accountNumber;
	private String firstName;
	private String lastName;
	private MultipartFile multiPart;
	
	public String getImeiNumber() {
		return imeiNumber;
	}
	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
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
	public MultipartFile getMultiPart() {
		return multiPart;
	}
	public void setMultiPart(MultipartFile multiPart) {
		this.multiPart = multiPart;
	}
	
	

}
