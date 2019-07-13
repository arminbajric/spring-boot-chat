package com.realtime.chat.service.common;

public class userLogin {
    boolean email;
    boolean password;

    public userLogin(boolean email, boolean password) {
        this.email = email;
        this.password = password;
    }

    public boolean isEmail() {
        return email;
    }

    public void setEmail(boolean email) {
        this.email = email;
    }

    public boolean isPassword() {
        return password;
    }

    public void setPassword(boolean password) {
        this.password = password;
    }
}
