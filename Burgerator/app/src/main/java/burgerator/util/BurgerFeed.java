package burgerator.util;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BurgerFeed extends Feed{

    private static final BurgerFeed FEED = new BurgerFeed();

    //return this instance of burgerFeed
    public static BurgerFeed instance(){
        return FEED;
    }

    private BurgerFeed(){
        this =  new super();
    }

    public void setFeed(JSONObject j){
        try {
            //accesses the burgers jsonarray and creates an array of Burger(s)
            super.json = j.getJSONObject("result").getJSONObject("content");
            int size = json.getJSONArray("burger").length();
            int count = 0;

            burgers = new ArrayList<>();
            JSONArray burgerArray = json.getJSONArray("burger");
            while (count < size) {
                //creates a Burger JSONObject for each burger in the JSONArray - necessary for specific getters for burger
                JSONObject burger = burgerArray.getJSONObject(count);
                Burger b = new Burger();
                b.setJSON(burger);
                burgers.add(b);
                count++;
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
        return this.burgers.size();
    }

    public Burger get(int _index){
        if (burgers.isEmpty()){
            return null;
        }else{
            return burgers.get(_index);
        }
    }


}
