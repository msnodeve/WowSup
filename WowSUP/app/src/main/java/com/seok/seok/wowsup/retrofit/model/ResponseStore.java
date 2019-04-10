package com.seok.seok.wowsup.retrofit.model;

public class ResponseStore {
    //스토어에 들어갈 모델 클래스
    private int storyID;
    private int state;

    public int getStoryID() {
        return storyID;
    }

    public void setStoryID(int storyID) {
        this.storyID = storyID;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
