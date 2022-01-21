package com.example.healthwithme.model;

public class DrinkWater implements Comparable<DrinkWater> {
    private String uid;
    private int waterVolume;
    private String date;
    private String time;

    public DrinkWater() {
    }

    public DrinkWater(int waterVolume, String date, String time) {
        this.waterVolume = waterVolume;
        this.date = date;
        this.time = time;
    }
    public DrinkWater(String uid,int waterVolume, String date, String time) {
        this.uid = uid;
        this.waterVolume = waterVolume;
        this.date = date;
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getWaterVolume() {
        return waterVolume;
    }

    public void setWaterVolume(int waterVolume) {
        this.waterVolume = waterVolume;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int compareTo(DrinkWater drinkWater) {
        return drinkWater.getTime().compareTo(getTime());
    }
}
