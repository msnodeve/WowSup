package com.seok.seok.wowsup.utilities;

public class NoticeData {
    //친구 목록을 어댑터에 담기위한 클래스
    private String applyer;
    private String userSelfish;

    public NoticeData(String applyer, String userSelfish) {
        this.applyer = applyer;
        this.userSelfish = userSelfish;
    }

    public String getApplyer() {
        return applyer;
    }

    public void setApplyer(String applyer) {
        this.applyer = applyer;
    }

    public String getUserSelfish() {
        return userSelfish;
    }

    public void setUserSelfish(String userSelfish) {
        this.userSelfish = userSelfish;
    }
}
