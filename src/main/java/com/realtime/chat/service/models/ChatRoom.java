package com.realtime.chat.service.models;

import org.springframework.data.annotation.Id;

import java.util.List;

public class ChatRoom {
    @Id
    private String id;
    String room;
    List<UsersModel> users;

    public ChatRoom() {
    }

    public ChatRoom(String room, List<UsersModel> users) {
        this.room = room;
        this.users = users;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public List<UsersModel> getUsers() {
        return users;
    }

    public void setUsers(List<UsersModel> users) {
        this.users = users;
    }
}
