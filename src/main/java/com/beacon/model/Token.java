package com.beacon.model;


public class Token {

    private String tokenNumber;
    private String accountNumber;

    public Token(String tokenNumber, String accountNumber) {
        this.tokenNumber = tokenNumber;
        this.accountNumber = accountNumber;
    }

    public String getTokenNumber() {
        return tokenNumber;

    }

    public String getAccountNumber() {
        return accountNumber;
    }

}
