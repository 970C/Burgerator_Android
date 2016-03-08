package burgerator.control;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import org.json.JSONObject;

import burgerator.db.BurgerDB;
import burgerator.util.*;
import burgerator.yelp.YelpRestaurantListGpsRequest;
import burgerator.yelp.YelpRestaurantListStringRequest;

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
        //burgerDB.getBurgerFeed(null, "harokevin@yahoo.com", "1", "true",
        burgerDB.getBurgerFeed(null, user.getEmail(), "1", "true",
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
        //TODO test app with user.getEmail()
        burgerDB.getTopBurgers(null, user.getEmail(), "1",
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

    public void requestSocialLogin(Context _c, String email, String token, final Callback callback){
        //TODO: sanitate and validate the input
        burgerDB = new BurgerDB(_c);
        burgerDB.socialLogin(email, token, new BurgerDB.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                Log.d("Controller.requestSocialLogin()", result.toString());
                user.setUser(result);
                callback.onSuccess(result);
            }
        });
    }

    public void requestEmailRegister(Context _c,
                                     String userEmail, String firstName,
                                     String lastName, String password,
                                     String zip, final Callback callback ){
        //TODO: sanitate and validate the input
        burgerDB = new BurgerDB(_c);
        burgerDB.emailRegister(userEmail, firstName, lastName, password, zip,
                new BurgerDB.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        Log.d("Controller.requestSocialLogin()", result.toString());
                        user.setUser(result);
                        callback.onSuccess(result);
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
    public void setUserName(String name){
        user.setUserName(name);
    }

    public boolean isAuthenticated(){
        if(user.getResult().equalsIgnoreCase("1")) {
            return true;
        }
        return false;
    }

    /**
     * Put in a request to yelp api to return a list of burger restaurants given a location
     * @param locationToQuery A location object to pass to yelp to find burgers
     * @param _c A callback function to return request data to
     * @return a list of restaurants encoded as json objects returned by callback
     */
    public void requestYelpRestaurantList(Location locationToQuery, final Callback _c ){
        new YelpRestaurantListGpsRequest(_c).execute(locationToQuery);
    }

    /**
     * Put in a request to yelp api to return a list of burger restaurants given a location
     * @param locationToQuery A location represented as a string to pass to yelp to find burgers
     *          ex: "Ellensburg, Wa", "98926"
     * @param _c A callback function to return request data to
     * @return a list of restaurants encoded as json objects returned by callback
     */
    public void requestYelpRestaurantList(String locationToQuery, final Callback _c ){
        new YelpRestaurantListStringRequest(_c).execute(locationToQuery);
    }
}

