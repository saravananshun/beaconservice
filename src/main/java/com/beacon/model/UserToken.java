package com.beacon.model;


import org.springframework.data.annotation.Id;

public class UserToken {


    @Id
    private Integer id;

    private Integer token;

    public Integer getToken() {
        return token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setToken(Integer token) {
        this.token = token;
    }
}
