package burgerator.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.luis.burgerator.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import burgerator.control.Controller;
import burgerator.util.Burger;
import burgerator.db.BurgerDB;
import burgerator.util.BurgerFeed;
import burgerator.util.Callback;
import burgerator.util.Feed;
import burgerator.util.FeedAdapter;
import burgerator.util.ImageLoadTask;
import burgerator.util.LoadingDialog;
import burgerator.util.Top10Adapter;

public class FeedActivity extends Activity {

    private ListView mListView;
    private FeedAdapter mAdapter;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        final Typeface eastwood = Typeface.createFromAsset(getAssets(), "fonts/Eastwood.ttf");

        mLoadingDialog = new LoadingDialog(this);
        mLoadingDialog.start();

        // Find the ListView
        mListView = (ListView) findViewById(R.id.feedListView);
        mListView.setVerticalScrollBarEnabled(false);

        //Attach the adaper to the list view -> in: OnFeedResponse()
        ArrayList dummyBurgers = new ArrayList<>();
        mAdapter = new FeedAdapter(this, dummyBurgers );
        mListView.setAdapter(mAdapter);

        //Add the string to the banner
        TextView bannerBurgerFeed = (TextView)findViewById(R.id.et_banner);
        bannerBurgerFeed.setText(R.string.title_activity_burger_feed);
        bannerBurgerFeed.setTextSize((float)30.0);
        bannerBurgerFeed.setGravity(Gravity.CENTER);
        bannerBurgerFeed.setTypeface(eastwood);


        // Initializes button views and their onClickListeners
        // button to go to findABurger
        Button findABurgerButton = (Button) findViewById(R.id.btn_search_activity);
        findABurgerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        // button to go to burger_feed
        Button burgerFeedButton = (Button) findViewById(R.id.btn_feed_activity);
        burgerFeedButton.setBackgroundResource(R.mipmap.feed_button_on);
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

    @Override
    protected void onStart() {
        super.onStart();

        // HTTP Request to get the burger feed
        /*
        final BurgerDB feedRequest = new BurgerDB(getApplicationContext());
        feedRequest.getBurgerFeed(null, "harokevin@yahoo.com", "", "",
                new BurgerDB.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        onFeedResponse(result);
                    }
                });
        */
        //make request to controller to call burgerdb for http req
        Controller.instance().requestBurgerFeed(getApplicationContext(), new Callback() {
            @Override
            public void onSuccess(Object result) {
                //on successful callback:
                //  call onFeedResponse and pass it the just initialized feed
                onFeedResponse((Feed) result);
           }
        });

    }

    //called when the server returns the burger feed
    // OLD private void onFeedResponse(JSONObject response){
    private void onFeedResponse(Feed response){
        Log.d("Burgerator FeedActivity","onFeedResponse(): " + response.toString());

        try {
            if(response.size() > 0){
                //Get feed
                List<Burger> feedElements = Controller.instance().getbFeed().getAll();

                //refresh adapter data
                mAdapter = (FeedAdapter) mListView.getAdapter();
                mAdapter.clear();
                mAdapter.addAll(feedElements);
                mAdapter.notifyDataSetChanged();

                mLoadingDialog.stop();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }


}
