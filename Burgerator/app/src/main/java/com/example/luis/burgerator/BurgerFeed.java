package com.example.luis.burgerator;


import org.json.JSONObject;

public class BurgerFeed {

    private JSONObject json;
    //store individual burger JSONObjects in an array;
    private Burger[] burgers;

    //instantiate burgerFeed
    private static final BurgerFeed FEED = new BurgerFeed();

    private BurgerFeed(){
        this.json = new JSONObject();
    }

    //return this instance of burgerFeed
    public static BurgerFeed instance(){
        return FEED;
    }

    public void setFeed(JSONObject j){
        try {
            //accesses the burgers jsonarray and creates an array of Burger(s)
            this.json = j.getJSONObject("result").getJSONObject("content");
            int size = json.getJSONArray("burger").length();
            int count = 0;

            burgers = new Burger[size];
            while (count < size) {
                //creates a Burger JSONObject for each burger in the JSONArray - necessary for specific getters for burger
                burgers[count].setJSON(json.getJSONArray("burger").getJSONObject(count));
            }
        }catch(Exception e){
            System.out.println(e);
        }

    }

    //return a string value for a particular key if it exists
    private String getVal(String k){
        String result = "";
        try{
            result = json.getString(k);
        }catch (Exception e){
            System.out.println(e);
            //Log.getInstance().addLog("error " + l + ", " + e + "\n");
        }
        return result;
    }

    public int size(){
        return this.burgers.length;
    }

    public Burger get(int _index){
        return burgers[_index];
    }


}
