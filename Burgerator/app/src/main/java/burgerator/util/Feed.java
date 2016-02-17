package burgerator.util;

import org.json.JSONArray;
import org.json.JSONObject;
import android.util.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonathan on 2/9/2016.
 * Feed class for BurgerFeed and TopTenFeed
 *
 */
public class Feed {
    //container for JSONObjects
    private JSONObject json;
    //store individual burger JSONObjects in an array;
    private List<Burger> burgers;

    /**
     * Default constructor for Feed. Instantiates a new ArrayList<JSONObject>()
     */
    public Feed (){
        this.burgers = new ArrayList<>();
    }

    /**
     * parses a JSONArray into Burger(s) and add them to the burger list
     * @param _obj is a JSONObject
     */

    private void addBurgersToList(JSONObject _obj){

        try{
            //accesses the burgers jsonarray and creates an array of Burger(s)
            this.json = _obj.getJSONObject("result").getJSONObject("content");
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
        }catch (Exception e) {
            Log.d("addBurgersToList; ", e.getMessage());
        }
    }

    /**
     * adds a new JSONObject to be parsed and added to the list of burgers
     *@param _obj is a JSONObject
     */
    public void addJSON (JSONObject _obj){
        try{
            addBurgersToList(_obj);
        }catch (Exception e) {
            Log.d("addJSON ", e.getMessage());
        }
    }

    /**
     * Deprecated - included for compatibility, sets the JSONObject
     * @param _obj is a JSONObject
     */
    public void setJSON (JSONObject _obj) {
        try{
            addBurgersToList(_obj);
        }catch (Exception e) {
            Log.d("setJSON", e.getMessage());
        }
    }

    /**
     * return a string value for a particular key if it exists
     * @param k String key
     * @return String result
     */
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

    /**
     * returns the size of the ArrayList
     * @return size
     */
    public int size(){
        return this.burgers.size();
    }

    /**
     * returns a Burger b by index
     * @param _index int index for burgers ArrayList
     * @return Burger b
     */
    public Burger get(int _index){
        if (burgers.isEmpty()){
            return null;
        }else{
            return burgers.get(_index);
        }
    }

}
