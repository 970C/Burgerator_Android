package com.example.luis.burgerator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

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
        View restaurantView = inflater.inflate(R.layout.activity_top10_burger_feed_containers, null);


        // Add the forms/content to the ScrollView
        sv.addView(restaurantView);


        // Display the view
        setContentView(v);

        //set the picture for this burger entry
        String burgerUrl0 = "https://s3.amazonaws.com/burgerdev/uploads/image_1453390645.png";
        ImageView burgerImg0 = (ImageView)findViewById(R.id.imgv_burger_picture0);
        new ImageLoadTask(burgerUrl0, burgerImg0).execute();

        String burgerUrl1 = "https://s3.amazonaws.com/burgerdev/uploads/image_1453390645.png";
        ImageView burgerImg1 = (ImageView)findViewById(R.id.imgv_burger_picture1);
        new ImageLoadTask(burgerUrl1, burgerImg1).execute();



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
}
