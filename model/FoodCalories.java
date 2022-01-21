package com.example.healthwithme.model;

public class FoodCalories  implements Comparable<FoodCalories>{
    private String uid;
    private int FoodCalories;
    private String Foodname;
    private String portion;
    private String date;
    private String time;

    public FoodCalories(){

    }
    public FoodCalories(String Foodname,String portion,int FoodCalories,String date,String time){
        this.Foodname = Foodname;
        this.portion = portion;
        this.FoodCalories = FoodCalories;
        this.date = date;
        this.time = time;
    }

    public FoodCalories(String uid,String Foodname,String portion,int FoodCalories,String date,String time){
        this.uid = uid;
        this.Foodname = Foodname;
        this.portion = portion;
        this.FoodCalories = FoodCalories;
        this.date = date;
        this.time = time;
    }

    public int getFoodCalories() {
        return FoodCalories;
    }

    public void setFoodCalories(int foodCalories) {
        FoodCalories = foodCalories;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFoodname() {
        return Foodname;
    }

    public void setFoodname(String foodname) {
        Foodname = foodname;
    }

    public String getPortion() {
        return portion;
    }

    public void setPortion(String potion) {
        this.portion = potion;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    @Override
    public int compareTo(FoodCalories foodCalories) {
        return foodCalories.getTime().compareTo(getTime());
    }
}
