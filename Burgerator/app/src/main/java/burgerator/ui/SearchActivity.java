package burgerator.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luis.burgerator.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import burgerator.util.SearchAdapter;
import burgerator.util.Top10Adapter;
import burgerator.yelp.YelpCallback;
import burgerator.yelp.YelpRestaurantListRequest;
import burgerator.yelp.YelpSingleRestaurantRequest;

public class SearchActivity extends Activity {

    private ListView mListView;
    private SearchAdapter mAdapter;
    private List mRestaurants = new ArrayList<>();
    private YelpResponseCallbacks mCallbacks;

    private class YelpResponseCallbacks implements YelpCallback {
        @Override
        public void onSuccess(JSONObject result) {onRestaurantListResponse(result);}
        @Override
        public void onSuccess(List<JSONObject> result) {onSingleRestaurantResponse(result);}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Find the ListView
        mListView = (ListView) findViewById(R.id.searchListView);

        //Attach the adapter to the list view and requisition request
        mCallbacks = new YelpResponseCallbacks();
        new YelpRestaurantListRequest(mCallbacks).execute("Seattle, WA");
        ArrayList dummyBurgers = new ArrayList<>();
        mAdapter = new SearchAdapter(this, dummyBurgers );
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Toast.makeText(SearchActivity.this, "List Item Clicked", Toast.LENGTH_LONG).show();
            }
        });

        //Add the string to the banner
        TextView bannerBurgerFeed = (TextView)findViewById(R.id.et_banner);
        bannerBurgerFeed.setText(R.string.find_a_burger);
        bannerBurgerFeed.setTextSize((float)30.0);
        bannerBurgerFeed.setGravity(Gravity.CENTER);

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
    }


    public void onRestaurantListResponse(JSONObject response){
        Log.d("SearchActiivity oRLR", response.toString());
    }
    public void onSingleRestaurantResponse(List<JSONObject> response){
        Log.d("SearchActiivity oSRR", response.toString());

        //Pass a fresh data set to the adapter to load
        mRestaurants = response;
        mAdapter = (SearchAdapter) mListView.getAdapter();
        mAdapter.clear();
        mAdapter.addAll(mRestaurants);
        mAdapter.notifyDataSetChanged();
    }

}
