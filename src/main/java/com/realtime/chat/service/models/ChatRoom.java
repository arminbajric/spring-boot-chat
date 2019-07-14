package com.realtime.chat.service.models;

import org.springframework.data.annotation.Id;

import java.util.List;

public class ChatRoom {
    @Id
    private String id;
    String room;


    public ChatRoom() {
    }

    public ChatRoom(String room) {
        this.room = room;
    }

    public ChatRoom(String room, List<UsersModel> users) {
        this.room = room;

    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

}
