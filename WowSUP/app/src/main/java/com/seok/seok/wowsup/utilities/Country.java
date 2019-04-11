package com.seok.seok.wowsup.utilities;

//국가를 받아올 클래스
public class Country {

    private String country;
    private int imgCountry;

    public Country(String name, int imgCountry)
    {
        this.country = name;
        this.imgCountry = imgCountry;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getImgCountry() {
        return imgCountry;
    }

    public void setImgCountry(int imgCountry) {
        this.imgCountry = imgCountry;
    }
}