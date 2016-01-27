package com.example.luis.burgerator;

import org.json.JSONObject;


public class Burger {

    private JSONObject json;

    //private static final User USER = new User();

    public Burger(){
        this.json = new JSONObject();
    }

    public void setJSON(JSONObject j){
        this.json = j;
    }

    //return a string value for a particular key if it exists
    private String getVal(String k){
        String result = "";
        try{
            result = json.getString(k);
            //return result;
        }catch (Exception e){
            System.out.println(e);
        }
        return result;
    }

    public String getId(){
        return getVal("burgerId");
    }
    public String getRestaurantID(){
        return getVal("restaurantId");
    }
    public String getUserEmail(){
        return getVal("userEmail");
    }
    public String getBurgerName(){
        return getVal("burgerName");
    }
    public String getRating(){
        return getVal("rating");
    }
    public String getImageURL(){
        return getVal("imageURL");
    }
    public String getRestaurantName(){
        return getVal("restaurantName");
    }
    public String getRestaurantImageURL(){
        return getVal("restaurantImageURL");
    }
    public String getRestaurantAddress(){
        return getVal("restaurantAddress");
    }
    public String getRestaurantCity(){
        return getVal("restaurantCity");
    }
    public String getRestaurantZip(){
        return getVal("restaurantZip");
    }
    public String getDate(){
        return getVal("date");
    }
    public String getUserPhoto(){
        return getVal("userPhoto");
    }
    public String getPound(){
        return getVal("pound");
    }
    public String getIsPound(){
        return getVal("ispound");
    }





}
