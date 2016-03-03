package burgerator.ui;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;

import burgerator.control.Controller;
import burgerator.util.Burger;
import burgerator.db.BurgerDB;
import burgerator.util.BurgerFeed;
import burgerator.util.Callback;
import burgerator.util.Feed;
import burgerator.util.ImageLoadTask;
import burgerator.util.LoadingDialog;
import burgerator.util.Top10Adapter;

public class Top10Activity extends Activity {

    private ListView listView;
    private Top10Adapter mAdapter;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top10);

        mLoadingDialog = new LoadingDialog(this);
        mLoadingDialog.start();

        // Find the ListView
        listView = (ListView) findViewById(R.id.top10ListView);

        //Attach the adaper to the list view -> in: OnFeedResponse()
        ArrayList dummyBurgers = new ArrayList<>();
        mAdapter = new Top10Adapter(this, dummyBurgers );
        listView.setAdapter(mAdapter);

        //Add the string to the banner
        TextView bannerBurgerFeed = (TextView)findViewById(R.id.et_banner);
        bannerBurgerFeed.setText(R.string.title_activity_top_burgers);
        bannerBurgerFeed.setTextSize((float)30.0);
        bannerBurgerFeed.setGravity(Gravity.CENTER);

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
        topBurgersButton.setBackgroundResource(R.mipmap.top10_button_on);
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
        /* OLD
        final BurgerDB feedRequest = new BurgerDB(getApplicationContext());
        feedRequest.getTopBurgers(null, "harokevin@yahoo.com", "",
                new BurgerDB.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        onFeedResponse(result);
                    }
                });
        */
        Controller.instance().requestTopTenFeed(getApplicationContext(), new Callback() {
            @Override
            public void onSuccess(Object result) {
                //on successful callback, call onfeedresponse and pass it our populated feed
                onFeedResponse( (Feed) result );
            }
        });

    }

    //called when the server returns the burger feed
    //private void onFeedResponse(JSONObject response){
    private void onFeedResponse(Feed response){
        Log.d("Burgerator Top10Act","onFeedResponse(): " + response.toString());

        /////Initialize feed - populate views with burger data from response
        //BurgerFeed.instance().setFeed(response);

        //Create burger array
        ArrayList<Burger> burgers = new ArrayList<Burger>();
        System.out.println(response.size());
        for(int i=0; i<10; i++)
            burgers.add(response.get(i));


        mAdapter = (Top10Adapter) listView.getAdapter();
        mAdapter.clear();
        mAdapter.addAll(burgers);
        mAdapter.notifyDataSetChanged();
        //Attach the adaper to the list view
        //listView.setAdapter(mAdapter);


        //Get current layout
        //RelativeLayout feedElement = (RelativeLayout) findViewById(containers[i]);

        //Get current burger
        //Burger burger = BurgerFeed.instance().get(i);
        /*
        //TODO: Set rank image/photo
        int rank = R.drawable.rank10;
        switch (i){
            case 0:
                rank = R.drawable.rank1;
                break;
            case 1:
                rank = R.drawable.rank2;
                break;
            case 2:
                rank = R.drawable.rank3;
                break;
            case 3:
                rank = R.drawable.rank4;
                break;
            case 4:
                rank = R.drawable.rank5;
                break;
            case 5:
                rank = R.drawable.rank6;
                break;
            case 6:
                rank = R.drawable.rank7;
                break;
            case 7:
                rank = R.drawable.rank8;
                break;
            case 8:
                rank = R.drawable.rank9;
                break;
            case 9:
                rank = R.drawable.rank10;
                break;
            default:
                rank = R.drawable.rank1;

                ImageView rankPhoto = (ImageView)feedElement.findViewById(R.id.imgv_burger_ranking);
                rankPhoto.setImageResource(rank);
                /*ImageView userPhoto = (ImageView)feedElement.findViewById(R.id.imgv_user_image);
                String userPhotoUrl = burger.getUserPhoto();
                new ImageLoadTask(userPhotoUrl, userPhoto).execute();/////////

                //Set restaurant name
                TextView restaurantName = (TextView) feedElement.findViewById(R.id.restaurant_name);
                restaurantName.setText(burger.getRestaurantName());

                //Set burger name
                TextView burgerName = (TextView) feedElement.findViewById(R.id.burger_name);
                burgerName.setText(burger.getBurgerName());

                //Set restaurant address
                TextView restaurantAddress = (TextView) feedElement.findViewById(R.id.restaurant_address);
                restaurantAddress.setText(burger.getRestaurantAddress());*/
        mLoadingDialog.stop();
    }
}

