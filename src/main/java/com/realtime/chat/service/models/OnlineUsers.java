package com.realtime.chat.service.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "usersOnline")
public class OnlineUsers {
    @Id
    private String id;
    private String username;
    private String email;
    private String sessionid;

    public OnlineUsers( String username, String userEmail, String sessionid) {

        this.username = username;
        this.email = userEmail;
        this.sessionid = sessionid;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public OnlineUsers(String username) {
        this.username = username;
    }



    public OnlineUsers(String username, String userEmail) {
        this.username = username;
        this.email = userEmail;
    }

    public OnlineUsers() {
    }

    public String getUserEmail() {
        return email;
    }

    public void setUserEmail(String userEmail) {
        this.email = userEmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }




}
