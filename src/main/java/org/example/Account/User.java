package org.example.Account;

import java.sql.*;
import java.util.Scanner;

public class User {
    private String username;
    private String email;
    private String password;
    private String loginType; // CamelCase for consistency

    public User(String username, String email, String password, String loginType) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.loginType = loginType;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public String getLoginType() {
        return loginType;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
