package com.beacon.model;


import org.springframework.data.annotation.Id;

public class UserToken {

    @Id
    private Integer token;

    public Integer getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }
}
