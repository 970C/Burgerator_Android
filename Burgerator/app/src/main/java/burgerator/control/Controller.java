package burgerator.control;

import android.content.Context;

import org.json.JSONObject;

import burgerator.db.BurgerDB;
import burgerator.util.*;

/**
 * Created by Jonathan on 2/6/2016.
 * static class that routes all requests from UI
 */
public class Controller {

    private static final Controller CONTROLLER = new Controller();


    //containers for our data, We're going to use these more often than not hence,
    // instantiation here is fine
    private Feed bFeed =  new Feed();
    private Feed ttFeed = new Feed();

    private User user = new User();

    private BurgerDB burgerDB;

    private Controller(){

    }

    public Controller instance(){
        return CONTROLLER;
    }

    public void setBurgerFeed(JSONObject _json){
        this.bFeed.addJSON(_json);
    }

    public void setTopTenFeed(JSONObject _json){
        this.bFeed.addJSON(_json);
    }

    public void setUser(){

    }

    public Feed getbFeed(Context _c){
        burgerDB = new BurgerDB(_c);
        burgerDB.getTopBurgers(null, "harokevin@yahoo.com", "",
                new BurgerDB.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) {
                       // onFeedResponse(result);
                    }
                });


        return bFeed;
    }

    public Feed getTtFeed(){
        return ttFeed;
    }
}

