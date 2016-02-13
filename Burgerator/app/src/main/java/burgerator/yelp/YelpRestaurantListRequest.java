package burgerator.yelp;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 2/12/2016.
 */
public class YelpRestaurantListRequest extends AsyncTask<String,Void,List> {


    private List mRestaurants;
    private YelpCallback mCallback;
    private YelpAPI mYelpApiInstance;
    private String mLocation;

    public YelpRestaurantListRequest(YelpCallback callback){
        mRestaurants = new ArrayList();
        mCallback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List doInBackground(String... params) {

        //get first parameter passed in
        if(params.length > 0)
            mLocation = params[0];
        else
            throw new IllegalArgumentException("YelpRestaurantListRequest has no input parameters");

        // Creates a YelpAPI object that gets a list of restaurants
        mYelpApiInstance = new YelpAPI();
        mRestaurants = mYelpApiInstance.getRestaurants(mYelpApiInstance,mLocation);

        //Return restaurants
        return mRestaurants;
    }

    @Override
    protected void onPostExecute(List s) {
        super.onPostExecute(s);
        mCallback.onSuccess(s);
    }
}