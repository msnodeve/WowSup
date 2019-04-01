package com.seok.seok.wowsup.utilities;

import android.graphics.Point;
import android.util.Log;
import android.view.Display;

public class GlobalWowSup {
    private static GlobalWowSup globalWowToken = new GlobalWowSup();
    private int userWidth;
    private int userHeight;
    private int token;
    private String id;
    private String userEmail;
    private String imageURL;

    private GlobalWowSup() {
        Log.d("WowSup_GlobalSingleton_INIT", "Start");
    }

    public static GlobalWowSup getInstance() {
        return globalWowToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public int getUserWidth() {
        return userWidth;
    }

    public void setUserWidth(int userWidth) {
        this.userWidth = userWidth;
    }

    public int getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(int userHeight) {
        this.userHeight = userHeight;
    }

    public void setScreenSize(Display display) {
        Point size = new Point();
        display.getSize(size);
        this.userWidth = size.x;
        this.userHeight = size.y;
    }
}
