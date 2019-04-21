package com.seok.seok.wowsup.retrofit.model;

public class ResponseChat {
    private String friendNick;
    private String imageURL;
    private String friend;
    private String selfish;
    private String msg;
    private int state;

    public String getFriendNick() {
        return friendNick;
    }

    public void setFriendNick(String friendNick) {
        this.friendNick = friendNick;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public String getSelfish() {
        return selfish;
    }

    public void setSelfish(String selfish) {
        this.selfish = selfish;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
