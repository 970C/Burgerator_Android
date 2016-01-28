package com.example.luis.burgerator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;

public class Top10Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top10);

        /*This indented block should come before the onClick listeners before
        the onClick listeners wont trigger.*/
        // Adding custom elements to a ScrollView
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_top10, null);

        // Find the ScrollView
        ScrollView sv = (ScrollView) v.findViewById(R.id.top10ScrollView);


        // Inflate the first 10 boxes of the scroll view
        View restaurantView = inflater.inflate(R.layout.activity_top10_scroll_content, null);


        // Add the forms/content to the ScrollView
        sv.addView(restaurantView);


        // Display the view
        setContentView(v);


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
        final BurgerDB feedRequest = new BurgerDB(getApplicationContext());
        feedRequest.getTopBurgers(null, "harokevin@yahoo.com", "",
                new BurgerDB.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        onFeedResponse(result);
                    }
                });

    }

    //called when the server returns the burger feed
    private void onFeedResponse(JSONObject response){
        Log.d("Burgerator Top10Activity","onFeedResponse(): " + response.toString());

        /////Initialize feed - populate views with burger data from response
        BurgerFeed.instance().setFeed(response);

        int[] containers = {R.id.container0,R.id.container1,R.id.container2,R.id.container3,R.id.container4,R.id.container5,R.id.container6,R.id.container7,R.id.container8,R.id.container9};

        for(int i=0; i<10; i++) {
                //Get current layout
                RelativeLayout feedElement = (RelativeLayout) findViewById(containers[i]);

                //Get current burger
                Burger burger = BurgerFeed.instance().get(i);

                //TODO: Set rank image/photo
                /*ImageView userPhoto = (ImageView)feedElement.findViewById(R.id.imgv_user_image);
                String userPhotoUrl = burger.getUserPhoto();
                new ImageLoadTask(userPhotoUrl, userPhoto).execute();*/

                //Set restaurant name
                TextView restaurantName = (TextView) feedElement.findViewById(R.id.restaurant_name);
                restaurantName.setText(burger.getRestaurantName());

                //Set burger name
                TextView burgerName = (TextView) feedElement.findViewById(R.id.burger_name);
                burgerName.setText(burger.getBurgerName());

                //Set restaurant address
                TextView restaurantAddress = (TextView) feedElement.findViewById(R.id.restaurant_address);
                restaurantAddress.setText(burger.getRestaurantAddress());

                //Set burger image/photo
                ImageView burgerPhoto = (ImageView) feedElement.findViewById(R.id.imgv_burger_picture);
                String burgerPhotoUrl = burger.getImageURL();
                new ImageLoadTask(burgerPhotoUrl, burgerPhoto).execute();
        }
    }
}
