package com.google.privatesharingapp.bean;

import androidx.annotation.NonNull;

import org.litepal.crud.LitePalSupport;

public class UserBean extends LitePalSupport {
    private String userName;
    private String password;
    private String head;
    private String nickName;
    private String phone;

    public UserBean(String userName, String password, String head, String nickName, String phone) {  //Constructor to create a UserBean object and initialize its properties
        this.userName = userName;
        this.password = password;
        this.head = head;
        this.nickName = nickName;
        this.phone = phone;
    }

    @NonNull
    @Override
    public String toString() {
        //Returns the properties of the UserBean object as string
        return "UserBean{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", head='" + head + '\'' +
                ", nickName='" + nickName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
