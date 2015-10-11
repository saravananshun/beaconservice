package com.beacon.model;

public class CustomerServiceQueue {
	private String tokenNumber;
	private String accountNumber;
	private String status;
	
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
	
	
}
