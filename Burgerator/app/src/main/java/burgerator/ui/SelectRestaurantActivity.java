package burgerator.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luis.burgerator.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import burgerator.control.Controller;
import burgerator.util.Callback;
import burgerator.util.LoadingDialog;
import burgerator.util.Restaurants;
import burgerator.util.SearchAdapter;
import burgerator.yelp.YelpRestaurant;

/**
 * Created by Luis on 2/15/2016.
 */
public class SelectRestaurantActivity extends Activity {

    private ListView mListView;
    private SearchAdapter mAdapter;
    private EditText mSearch;
    private LoadingDialog mLoadingDialog;
    private List mRestaurants;
    private Intent mReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Typeface eastwood = Typeface.createFromAsset(getAssets(), "fonts/Eastwood.ttf");
        mLoadingDialog = new LoadingDialog(this);

        // Display the view
        setContentView(R.layout.activity_rate_select_restaurant);

        //Add the string to the banner
        TextView bannerBurgerFeed = (TextView)findViewById(R.id.rest_banner);
        bannerBurgerFeed.setText(R.string.title_activity_select_restaurant);
        bannerBurgerFeed.setGravity(Gravity.CENTER);
        bannerBurgerFeed.setTypeface(eastwood);

        mReturn = new Intent();
        ImageButton back = (ImageButton)findViewById(R.id.imgbtn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mReturn.getStringExtra("result") == null || mReturn.getStringExtra("result").isEmpty())
                    setResult(Activity.RESULT_CANCELED, mReturn);
                else
                    setResult(Activity.RESULT_OK, mReturn);

                finish();
            }
        });

        //Attach the adapter to the list view
        mRestaurants = new ArrayList();
        if(!Restaurants.instance().getList().isEmpty()){
            mRestaurants = Restaurants.instance().getList();
        }
        // Find the ListView
        mListView = (ListView) findViewById(R.id.searchListView);
        mAdapter = new SearchAdapter(this, mRestaurants);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "List Item "+position+" Clicked", Toast.LENGTH_LONG).show();
                JSONObject resultRestaurant =  (JSONObject)mRestaurants.get(position);
                startActivity(new Intent(getApplicationContext(),SelectBurgerActivity.class));
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
        Log.d("SelectRestaurantActiivity oRLR", response.toString());

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

}
