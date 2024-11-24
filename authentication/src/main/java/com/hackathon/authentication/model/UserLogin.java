package com.hackathon.authentication.model;

public class UserLogin {
    private String username;
    private String password;

    public UserLogin(String login, String password) {
        this.username = login;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setLogin(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
