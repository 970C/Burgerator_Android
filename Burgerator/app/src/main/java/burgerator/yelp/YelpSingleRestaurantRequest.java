package burgerator.yelp;

import android.os.AsyncTask;

import org.json.JSONObject;


public class YelpSingleRestaurantRequest extends AsyncTask<String,Void,JSONObject> {

    private JSONObject mRestaurant;
    private YelpCallback mCallback;
    private YelpAPI mYelpApiInstance;
    private String mBusinessId;


    public YelpSingleRestaurantRequest(YelpCallback callback){
        mRestaurant = new JSONObject();
        mCallback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        //get first parameter passed in
        if(params.length > 0)
            mBusinessId = params[0];
        else
            throw new IllegalArgumentException("YelpSingleRestaurantRequest has no input parameters");

        // Creates a YelpAPI object that gets a list of restaurants
        mYelpApiInstance = new YelpAPI();
        mRestaurant = mYelpApiInstance.searchByBusinessId(mBusinessId);

        //Return restaurants too
        return mRestaurant;
    }

    @Override
    protected void onPostExecute(JSONObject s) {
        super.onPostExecute(s);
        mCallback.onSuccess(s);
    }
}