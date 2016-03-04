package burgerator.control;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import burgerator.db.BurgerDB;
import burgerator.util.*;

/**
 * Created by Jonathan on 2/6/2016.
 * static class that routes all requests from UI
 */
public class Controller implements Callback{

    @Override
    public void onSuccess(Object result) {

    }

    private static final Controller CONTROLLER = new Controller();


    //containers for our data, We're going to use these more often than not hence,
    // instantiation here is fine
    private Feed bFeed =  new Feed();
    private Feed ttFeed = new Feed();

    private User user = new User();

    //local storage
    private UserStorage stor = new UserStorage();

    private BurgerDB burgerDB;

    private Controller(){

    }

    public static Controller instance(){
        return CONTROLLER;
    }

    public void requestBurgerFeed(Context _c, final Callback callback){

        // HTTP Request to get the burger feed
        burgerDB = new BurgerDB(_c);
        //TODO replace static email with user email
        burgerDB.getBurgerFeed(null, "harokevin@yahoo.com", "1", "true",
                new BurgerDB.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        // add the JSONObject response (the feed in JSON form) to the bFeed
                        bFeed.addJSON(result);
                        //Pass the formatted feed in the callback
                        callback.onSuccess(bFeed);
                    }
                });
    }

    public void requestTopTenFeed(Context _c, final Callback callback){
        burgerDB = new BurgerDB(_c);
        //TODO replace static email with user email
        burgerDB.getTopBurgers(null, "harokevin@yahoo.com", "",
                new BurgerDB.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        // add the JSONObject response (the feed in JSON form) to the bFeed
                        //Pass the formatted feed in the callback
                        ttFeed.addJSON(result);
                        callback.onSuccess(ttFeed);
                    }
                });
    }

    public Feed getbFeed(){

        return bFeed;
    }

    public Feed getTtFeed(){

        return ttFeed;
    }

    //User
    public void requestUserAuth(String email, String pw, Context _c, final Callback callback){
        burgerDB = new BurgerDB(_c);
        burgerDB.emailLogin(null, email,
                pw,
                new BurgerDB.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        // add the JSONObject response (the feed in JSON form) to the bFeed
                        //Pass the formatted feed in the callback
                        Log.d("result: ", result.toString());
                        user.setUser(result);
                        callback.onSuccess(user);
                    }
                });
    }

    public User getUser(){
        return user;
    }

    public void setUser(JSONObject _obj){
        user.setUser(_obj);
    }

    public boolean isAuthenticated(){
        if(user.getResult().equalsIgnoreCase("1")) {
            return true;
        }
        return false;
    }
}

