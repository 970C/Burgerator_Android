package burgerator.db;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Interacts with the database API at http://default-environment-rp6pp3mmgc.elasticbeanstalk.com
 */
public class BurgerDB {

    public interface VolleyCallback{
        void onSuccess(JSONObject result);
    }

    private String mEndpoint = "http://default-environment-rp6pp3mmgc.elasticbeanstalk.com";
    private RequestQueue mRequestQueue;
    private Response.ErrorListener mErrListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("BurgerDbLog", "ERROR: "+error.getMessage());
        }
    };


    public BurgerDB(Context context){
        mRequestQueue = Volley.newRequestQueue(context);
    }


    /***
     * Returns the recent burgers(burgers the user has rated?) for a user
     * @param view
     * @param userEmail
     * @param page
     * @return - should return an object that contains:
     *                  a list of burger objects, and the boolean to hasNextPage
     */
    public void getUserRatedBurgers(View view, final String userEmail, final String page, final VolleyCallback callback){

        String endpointFile =  "/recentburgers.php";

        // Request parameters(body of request)
        Map<String,String> params = new HashMap<String,String>();
        //TODO: Check userEmail, and page  are safe to pass to the server
        params.put("useremail", userEmail);
        //params.put("page", page);


        // Create request and its response
        CustomRequest request = new CustomRequest(
                Request.Method.POST, mEndpoint+endpointFile, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // return a list of burger objects using the backend code
                        Log.d("BurgerDbLog", response.toString());

                        callback.onSuccess(response);
                    }
                },
                mErrListener);  //end of request arguments

        // Add the request to the request queue to be executed
        mRequestQueue.add(request);

        //TODO: Return the list of burgers and the hasNextPage boolean in a class

    }


    /**
     * Returns the burger feed for a user
     * @param userEmail - users email
     * @param page      - page of the feed to return. 1 page = 10 items
     * @param global    - set false
     * @return - should return an object that contains:
     *                  a list of burger objects, and the boolean to hasNextPage
     */
    public void getBurgerFeed(View view, final String userEmail, final String page, final String global, final VolleyCallback callback){

        // /feed.php only works when you only send in the email
        String endpointFile = "/feed.php";


        // Request parameters(body of request)
        Map<String,String> params = new HashMap<String,String>();
        //TODO: Check userEmail, page, and global are safe to pass to the server
        params.put("useremail", userEmail);
        params.put("page", page);
        params.put("global", global);

        // Create request and its response
        CustomRequest request = new CustomRequest(
                Request.Method.POST, mEndpoint+endpointFile, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // return a list of burger objects using the backend code
                        Log.d("BurgerDbLog", response.toString());

                        callback.onSuccess(response);
                    }
                },
                mErrListener);  //end of request arguments

        // Add the request to the request queue to be executed
        mRequestQueue.add(request);
        //TODO: Return the list of burgers and the hasNextPage boolean in a class
    }


    /**
     * Returns the burger feed for a user
     * @param userEmail - users email
     * @param page      - page of the feed to return. 1 page = 10 items
     * @return - should return an object that contains:
     *                  a list of burger objects, and the boolean to hasNextPage
     */
    public void getTopBurgers(View view, final String userEmail, final String page, final VolleyCallback callback){
        // /feed.php only works when you only send in the email
        String endpointFile = "/topratedburgers.php";


        // Request parameters(body of request)
        Map<String,String> params = new HashMap<String,String>();
        //TODO: Check userEmail, page, and global are safe to pass to the server
        params.put("useremail", userEmail);
        //params.put("page", page);

        // Create request and its response
        CustomRequest request = new CustomRequest(
                Request.Method.POST, mEndpoint+endpointFile, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // return a list of burger objects using the backend code
                        Log.d("BurgerDbLog getTopBurgers", response.toString());

                        callback.onSuccess(response);
                    }
                },
                mErrListener);  //end of request arguments

        // Add the request to the request queue to be executed
        mRequestQueue.add(request);
    }

    /***
     * renew forgotten password
     * @param view
     * @param userEmail
     * @return password renewal sucessfull
     */
    public void renewPassword(View view, final String userEmail, final VolleyCallback callback){
        String endpointFile = "/forgotten_password.php";

        // Request parameters(body of request)
        Map<String,String> params = new HashMap<String,String>();
        //TODO: Check userEmai is safe to pass to the server
        params.put("useremail", userEmail);

        // Create request and its response
        CustomRequest request = new CustomRequest(
                Request.Method.POST, mEndpoint+endpointFile, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // return an object that tells if it was sucessful or not
                        Log.d("BurgerDbLog", response.toString());

                        callback.onSuccess(response);
                    }
                },
                mErrListener);  //end of request arguments

        // Add the request to the request queue to be executed
        mRequestQueue.add(request);

        //TODO: return an object that tells if it was sucessful or not
    }

    /***
     * user email login
     * @param view
     * @param userEmail
     * @param userPassword
     * @return login sucessfull
     */
    public void emailLogin(View view, final String userEmail, final String userPassword, final VolleyCallback callback){

        String endpointFile = "/login.php";

        // Request parameters(body of request)
        Map<String,String> params = new HashMap<String,String>();
        //TODO: Check userEmail, and userPassword are safe to pass to the server
        params.put("useremail", userEmail);
        params.put("userpassword", userPassword);

        // Create request and its response
        CustomRequest request = new CustomRequest(
                Request.Method.POST, mEndpoint+endpointFile, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // return an object that contains the user information
                        Log.d("BurgerDbLog", response.toString());

                        callback.onSuccess(response);
                    }
                },
                mErrListener);  //end of request arguments

        // Add the request to the request queue to be executed
        mRequestQueue.add(request);

        //TODO: return an object that contains the user information
    }

    public void rate(Map<String,String> params, String imagePath, final VolleyCallback callback){
        String endpointFile = "/rate.php";
        final File imageFile = new File(imagePath);

        try {
            MultipartRequest request = new MultipartRequest(
                    mEndpoint + endpointFile, mErrListener, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // return an object that contains the user information
                            Log.d("BurgerDbLog", response.toString());

                            callback.onSuccess(response);
                        }
                    },imageFile, params);

            // Add the request to the request queue to be executed
            mRequestQueue.add(request);
        }catch(Exception e){
            Log.e("Burgerator MulipartRequest Catch",e.toString());
        }
    }

    public void socialLogin(final String userEmail, final String userToken, final VolleyCallback callback){
        String endpointFile = "/social_login.php";

        // Request parameters(body of request)
        Map<String,String> params = new HashMap<String,String>();
        //TODO: Check userEmail, and userToken are safe to pass to the server
        params.put("useremail", userEmail);
        params.put("fbtoken", userToken);
        
        // Create request and its response
        CustomRequest request = new CustomRequest(
                Request.Method.POST, mEndpoint+endpointFile, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // return an object that contains the user information
                        Log.d("BurgerDbLog", response.toString());

                        callback.onSuccess(response);
                    }
                },
                mErrListener);  //end of request arguments

        // Add the request to the request queue to be executed
        mRequestQueue.add(request);
    }

}
