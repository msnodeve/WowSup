package com.seok.seok.wowsup.retrofit.model;

import java.util.Date;

public class ResponseLogin {

    private int state;
    private String userID;
    private String userEmail;
    private String userRegistryTime;
    private String msg;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserRegistryTime() {
        return userRegistryTime;
    }

    public void setUserRegistryTime(String userRegistryTime) {
        this.userRegistryTime = userRegistryTime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
