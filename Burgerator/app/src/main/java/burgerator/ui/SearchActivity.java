package burgerator.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luis.burgerator.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import burgerator.control.Controller;
import burgerator.gms.GPSTracker;
import burgerator.util.Callback;
import burgerator.util.LoadingDialog;
import burgerator.util.Restaurants;
import burgerator.util.SearchAdapter;
import burgerator.yelp.YelpCallback;
import burgerator.yelp.YelpRestaurant;

public class SearchActivity extends Activity {

    private ListView mListView;
    private SearchAdapter mAdapter;
    private List mRestaurants = new ArrayList<>();
    private YelpResponseCallbacks mCallbacks;
    private EditText mSearch;
    private LoadingDialog mLoadingDialog;
    private GPSTracker mLocation;

    private class YelpResponseCallbacks implements YelpCallback {
        @Override
        public void onSuccess(JSONObject result) {onSingleRestaurantResponse(result);}
        @Override
        public void onSuccess(List<JSONObject> result) {onRestaurantListResponse(result);}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Typeface eastwood = Typeface.createFromAsset(getAssets(), "fonts/Eastwood.ttf");

        mLoadingDialog = new LoadingDialog(this);
        mLoadingDialog.start();


        // Find the ListView
        mListView = (ListView) findViewById(R.id.searchListView);
        mListView.setVerticalScrollBarEnabled(false);

        //and requisition request for yelp restaurants
        mLocation =  new GPSTracker(getApplicationContext());
        Location currentLocation = mLocation.getLocation();
        String defaultLocation = "Ellensburg, WA";
        if(currentLocation != null){
            Controller.instance().requestYelpRestaurantList(
                    currentLocation,
                    new Callback() {
                        @Override
                        public void onSuccess(Object result) {
                            onRestaurantListResponse((List<JSONObject>) result);
                        }
                    }
            );
        }else if(!Controller.instance().getUser().getZip().equals("")){
            Controller.instance().requestYelpRestaurantList(
                    Controller.instance().getUser().getZip(),
                    new Callback() {
                        @Override
                        public void onSuccess(Object result) {
                            onRestaurantListResponse((List<JSONObject>) result);
                        }
                    }
            );
        }else{
            Controller.instance().requestYelpRestaurantList(
                    defaultLocation,
                    new Callback() {
                        @Override
                        public void onSuccess(Object result) {
                            onRestaurantListResponse((List<JSONObject>) result);
                        }
                    }
            );
        }


        //Attach the adapter to the list view
            // Find the ListView
            mListView = (ListView) findViewById(R.id.searchListView);
        ArrayList dummyBurgers = new ArrayList<>();
        mAdapter = new SearchAdapter(this, dummyBurgers );
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(SearchActivity.this, "List Item Clicked", Toast.LENGTH_LONG).show();
            }
        });

        //Add the string to the banner
        TextView bannerBurgerFeed = (TextView)findViewById(R.id.profile_banner);
        bannerBurgerFeed.setText(R.string.find_a_burger);
        bannerBurgerFeed.setTextSize((float)30.0);
        bannerBurgerFeed.setGravity(Gravity.CENTER);
        bannerBurgerFeed.setTypeface(eastwood);


        //map button
        ImageButton map = (ImageButton)findViewById(R.id.imgbtn_search);
        map.setBackgroundResource(R.drawable.map_icon);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivityMapView.class);
                startActivity(intent);
            }
        });

        // Initializes button views and their onClickListeners
        // button to go to findABurger
        Button findABurgerButton = (Button) findViewById(R.id.btn_search_activity);
        findABurgerButton.setBackgroundResource(R.mipmap.search_button_on);
        findABurgerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        // button to go to burger_feed
        Button burgerFeedButton = (Button) findViewById(R.id.btn_feed_activity);
        burgerFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FeedActivity.class);
                startActivity(intent);
            }
        });

        // button to go to burger_rating
        Button burgerRatingButton = (Button) findViewById(R.id.btn_rate_activity);
        burgerRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RateActivity.class);
                startActivity(intent);
            }
        });


        // button to go to top_burgers
        Button topBurgersButton = (Button) findViewById(R.id.btn_top10_activity);
        topBurgersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Top10Activity.class);
                startActivity(intent);
            }
        });

        // button to go to profile
        Button profileButton = (Button) findViewById(R.id.btn_profile_activity);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        mSearch = (EditText)findViewById(R.id.et_search);
        mSearch.setTypeface(eastwood);
        mSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mLoadingDialog.start();

                    Controller.instance().requestYelpRestaurantList(
                            mSearch.getText().toString(),
                            new Callback() {
                                @Override
                                public void onSuccess(Object result) {
                                    onRestaurantListResponse((List<JSONObject>) result);
                                }
                            }
                    );

                    return true;
                }
                return false;
            }
        });
    }

    //TODO: reengineer to get this inner class within YelpRestaurant or the Yelp package
    public class YelpRestaurantRankComparator implements Comparator<YelpRestaurant> {
        @Override
        public int compare(YelpRestaurant lhs, YelpRestaurant rhs) {
            return lhs.rating.compareTo(rhs.rating);
        }
    }

    public void onRestaurantListResponse(List<JSONObject> response) {
        Log.d("SearchActiivity oRLR", response.toString());

        //// sort list of restaurants by rating
                //JSONObjects -> YelpRestaurant objects
                Gson gson = new Gson();
                List<YelpRestaurant> restaurantList = new ArrayList<>();
                for(JSONObject restaurant: response ) {
                    restaurantList.add(gson.fromJson(restaurant.toString(), YelpRestaurant.class));
                }

                //sort list of YelpRestaurants with YelpRestaurantRankComparator
                Collections.sort(restaurantList, Collections.reverseOrder(new YelpRestaurantRankComparator()));

                //YelpRestaurant objects -> JSONObjects
                List<JSONObject> sortedRestaurants = new ArrayList<>();
                for(YelpRestaurant yR: restaurantList) {
                    // YelpRestaurant -> JSON String
                    String restaurant = gson.toJson(yR);

                    try {
                        // JSON String -> JSONObject
                        sortedRestaurants.add(new JSONObject(restaurant));
                    }catch(JSONException e){Log.e("SearchActivityy oRLR()",e.toString());}
                }
                mRestaurants = sortedRestaurants;
                // Add restaurant list to persistant session object
                Restaurants.instance().addList(mRestaurants);
                //Log.d("SrchActv.reataurants", "sanity");
                Log.d("SrchActv.reataurants", Restaurants.instance().getList().toString());
                Restaurants.instance().getCoordsList();

        // refresh the adapter
        mAdapter = (SearchAdapter) mListView.getAdapter();
        mAdapter.clear();
        mAdapter.addAll(mRestaurants);
        mAdapter.notifyDataSetChanged();

        mLoadingDialog.stop();
    }
    public void onSingleRestaurantResponse(JSONObject response){
        Log.d("SearchActiivity oSRR", response.toString());
    }

}
