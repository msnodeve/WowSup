package com.seok.seok.wowsup.utilities;

public class ChatData {
    //채팅에 필요한 모델 클래스
    private String date;
    private String email;
    private String text;

    public ChatData(){
        //Default
    }

    public ChatData(String email, String text, String date) {
        this.email = email;
        this.text = text;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}