package com.realtime.chat.service.models;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class ChatMessageModel {

    @Id
    private String id;

    private String text;
    private String author;
    private Date createDate;
    private String type;
    private String to;

    public String toToString() {
        return "ChatMessageModel{" +
                "text='" + text + '\'' +
                ", author='" + author + '\'' +
                ", createDate=" + createDate +
                ", type='" + type + '\'' +
                '}';
    }

    public ChatMessageModel() {
    }
    

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public ChatMessageModel(String text, String author, Date createDate) {
        this.text = text;
        this.author = author;
        this.createDate = createDate;
    }

    public ChatMessageModel(String text, String author, Date createDate, String type) {
        this.text = text;
        this.author = author;
        this.createDate = createDate;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + '\"' +
                ",\"text\":\"" + text + '\"' +
                ",\"author\":\"" + author + '\"' +
                ",\"createDate\":\"" + createDate + "\"" +
                '}';
    }
}