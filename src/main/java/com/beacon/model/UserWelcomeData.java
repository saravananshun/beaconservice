package com.beacon.model;

public class UserWelcomeData {
	private UserProfile userProfile;
	private BankService bankService;
	private Token token;

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	public BankService getBankService() {
		return bankService;
	}
	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}
	
}
