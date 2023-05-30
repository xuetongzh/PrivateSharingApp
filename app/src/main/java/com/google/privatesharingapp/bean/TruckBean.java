package com.google.privatesharingapp.bean;

import androidx.annotation.NonNull;

import org.litepal.crud.LitePalSupport;

public class TruckBean extends LitePalSupport {
    private int image;
    private String num;
    private String info;

    @NonNull
    @Override
    public String toString() {
        return "TruckBean{" +
                "image=" + image +
                ", num='" + num + '\'' +
                ", info='" + info + '\'' +
                '}';
    }

    public TruckBean(int image, String num, String info) {  //Constructor, initialize the object
        this.image = image;
        this.num = num;
        this.info = info;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
