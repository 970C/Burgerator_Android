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
public class Feed extends AbstractFeed<Burger> {
    //container for JSONObjects
    //private JSONObject json;
    //store individual burger JSONObjects in an array;
    //private List<Burger> list;

    /**
     * Default constructor for Feed. Instantiates a new ArrayList<JSONObject>()
     */
    public Feed (){
        list = new ArrayList<Burger>();
    }

    /**
     * parses a JSONArray into Burger(s) and add them to the burger list
     * @param _obj is a JSONObject
     */

    protected void addObjectsToList(JSONObject _obj){

        try{
            //accesses the burgers jsonarray and creates an array of Burger(s)
            this.json = _obj.getJSONObject("result").getJSONObject("content");
            int size = json.getJSONArray("burger").length();
            int count = 0;

            list = new ArrayList<>();
            JSONArray burgerArray = json.getJSONArray("burger");
            while (count < size) {
                //creates a Burger JSONObject for each burger in the JSONArray - necessary for specific getters for burger
                JSONObject burger = burgerArray.getJSONObject(count);
                Burger b = new Burger();
                b.setJSON(burger);
                list.add(b);
                count++;
            }
        }catch (Exception e) {
            Log.d("addBurgersToList; ", e.getMessage());
        }
    }


    /**
     * returns a Burger b by index
     * @param _index int index for burgers ArrayList
     * @return Burger b
     */
    public Burger get(int _index){
        if (list.isEmpty()){
            return null;
        }else{
            return list.get(_index);
        }
    }

    public List<Burger> getAll(){
        return list;
    }

}
