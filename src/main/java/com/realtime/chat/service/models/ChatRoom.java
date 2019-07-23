package com.realtime.chat.service.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Document("chatRoom")
public class ChatRoom implements Serializable {
    @Id
    private String id;
    private String room;
    private Date lastActivity;
    List<UsersModel> usersModels=new ArrayList<UsersModel>();


    public ChatRoom(String name, List<UsersModel> usersModels) {
        this.room = name;
        this.usersModels = usersModels;
    }

    public ChatRoom(String id, String room, Date lastActivity, List<UsersModel> usersModels) {
        this.id = id;
        this.room = room;
        this.lastActivity = lastActivity;
        this.usersModels = usersModels;
    }

    public ChatRoom(String room, Date lastActivity, List<UsersModel> usersModels) {
        this.room = room;
        this.lastActivity = lastActivity;
        this.usersModels = usersModels;
    }

    public Date getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Date lastActivity) {
        this.lastActivity = lastActivity;
    }

    public ChatRoom() {
    }

    public ChatRoom(String room) {
        this.room = room;
    }

    public String getName() {
        return room;
    }

    public void setName(String name) {
        this.room = name;
    }

    public List<UsersModel> getUsersModels() {
        return usersModels;
    }

    public void setUsersModels(List<UsersModel> usersModels) {
        this.usersModels = usersModels;
    }

    @Override
    public String toString() {
        return "ChatRoom{" +
                "id='" + id + '\'' +
                ", room='" + room + '\'' +
                ", lastActivity=" + lastActivity +
                ", usersModels=" + usersModels +
                '}';
    }
}
