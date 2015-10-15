package com.beacon.model;

import org.springframework.data.annotation.Id;

public class UserProfile {
	@Id
	private String ID;
	private String imeiNumber;
	private String accountNumber;
	private String firstName;
	private String lastName;
	private String bankCode;
	private byte[] imageBytes;
	private String tokenNumber;
	
	public String getTokenNumber() {
		return tokenNumber;
	}
	public void setTokenNumber(String tokenNumber) {
		this.tokenNumber = tokenNumber;
	}
	public byte[] getImageBytes() {
		return imageBytes;
	}
	public void setImageBytes(byte[] imageBytes) {
		this.imageBytes = imageBytes;
	}
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
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Override
	public String toString() {
		return "ID : " + ID + "-" + "Account Number : " + accountNumber;
	}
	
}
