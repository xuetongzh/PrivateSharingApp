package com.google.privatesharingapp.bean;

import androidx.annotation.NonNull;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class DeliveryBean extends LitePalSupport implements Serializable {
    private String userName;
    private String name;
    private String dateTime;
    private String pickLocation;
    private String pickLocationLat;
    private String pickLocationLon;
    private String dropLocation;
    private String dropLocationLat;
    private String dropLocationLon;
    private String weight;
    private String type;
    private String width;
    private String height;
    private String length;

    public DeliveryBean(String userName, String name, String dateTime, String pickLocation, String pickLocationLat, String pickLocationLon, String dropLocation, String dropLocationLat, String dropLocationLon, String weight, String type, String width, String height, String length) {
        // Constructor
        this.userName = userName;
        this.name = name;
        this.dateTime = dateTime;
        this.pickLocation = pickLocation;
        this.pickLocationLat = pickLocationLat;
        this.pickLocationLon = pickLocationLon;
        this.dropLocation = dropLocation;
        this.dropLocationLat = dropLocationLat;
        this.dropLocationLon = dropLocationLon;
        this.weight = weight;
        this.type = type;
        this.width = width;
        this.height = height;
        this.length = length;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getPickLocation() {
        return pickLocation;
    }

    public void setPickLocation(String pickLocation) { this.pickLocation = pickLocation; }

    public String getPickLocationLat() { return pickLocationLat; }

    public void setPickLocationLat(String pickLocationLat) { this.pickLocationLat = pickLocationLat; }

    public String getPickLocationLon() { return pickLocationLon; }

    public void setPickLocationLon(String pickLocationLon) { this.pickLocationLon = pickLocationLon; }

    public String getDropLocation() {
        return dropLocation;
    }

    public void setDropLocation(String dropLocation) { this.dropLocation = dropLocation; }

    public String getDropLocationLat() { return dropLocationLat; }

    public void setDropLocationLat(String dropLocationLat) { this.dropLocationLat = dropLocationLat; }

    public String getDropLocationLon() { return dropLocationLon; }

    public void setDropLocationLon(String dropLocationLon) { this.dropLocationLon = dropLocationLon; }

    public String getWeight() { return weight; }

    public void setWeight(String weight) { this.weight = weight; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getWidth() { return width; }

    public void setWidth(String width) { this.width = width; }

    public String getHeight() { return height; }

    public void setHeight(String height) { this.height = height; }

    public String getLength() { return length; }

    public void setLength(String length) { this.length = length; }
}
