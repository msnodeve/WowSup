package com.seok.seok.wowsup.retrofit.model;

public class ResponseWord {
    //글로벌 프래그먼트에 들어갈 모델 클래스
    private String key;
    private String value;
    private int state;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
