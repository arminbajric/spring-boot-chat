package com.realtime.chat.service.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "users")
public class UsersModel {
    @Id
    private String Id;
    private String email;
    private String username;
    private String password;

    public UsersModel() {
    }

    public UsersModel(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public UsersModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
