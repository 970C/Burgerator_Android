package burgerator.yelp;

import android.location.Location;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import burgerator.util.Callback;

/**
 * Created by Kevin on 2/12/2016.
 */
public class YelpRestaurantListGpsRequest extends AsyncTask<Location,Void,List> {


    private List mRestaurants;
    private Callback mCallback;
    private YelpAPI mYelpApiInstance;
    private Location mLocation;

    public YelpRestaurantListGpsRequest(Callback callback){
        mRestaurants = new ArrayList();
        mCallback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List doInBackground(Location... params) {

        //get first parameter passed in
        if(params.length > 0)
            mLocation = params[0];
        else
            throw new IllegalArgumentException("YelpRestaurantListGpsRequest has no input parameters");

        // Creates a YelpAPI object that gets a list of restaurants
        mYelpApiInstance = new YelpAPI();
        mRestaurants = mYelpApiInstance.getRestaurantsByGPS(mYelpApiInstance, mLocation);

        //Return restaurants
        return mRestaurants;
    }

    @Override
    protected void onPostExecute(List s) {
        super.onPostExecute(s);
        mCallback.onSuccess(s);
    }
}