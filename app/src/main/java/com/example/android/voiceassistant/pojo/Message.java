package com.example.android.voiceassistant.pojo;

import java.util.Date;

public class Message {
    private String text;
    private Date date;
    private Boolean isUser;

    public Message(String text, Boolean isUser) {
        this.text = text;
        this.date = new Date();
        this.isUser = isUser;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getUser() {
        return isUser;
    }

    public void setUser(Boolean user) {
        isUser = user;
    }
}
