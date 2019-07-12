package com.realtime.chat.service.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "usersOnline")
public class OnlineUsers {
    @Id
    private String id;
    private String username;
    private String sessionid;
    public OnlineUsers(String username) {
        this.username = username;
    }

    public OnlineUsers(String username, String sessionid) {
        this.username = username;
        this.sessionid = sessionid;
    }

    public OnlineUsers() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    @Override
    public String toString() {
        return "OnlineUsers{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", sessionid='" + sessionid + '\'' +
                '}';
    }
}
