package com.example.luis.burgerator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.json.JSONObject;

public class RateActivity extends Activity {

    private SeekBar tasteSeekBar;
    private SeekBar toppingSeekBar;
    private SeekBar bunSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*This indented block should come before the onClick listeners before
        the onClick listeners wont trigger.*/
        /* Setting up the activity's views */

                // Adding custom elements to a ScrollView
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.activity_rate, null);

                // Find the ScrollView
                ScrollView sv = (ScrollView) v.findViewById(R.id.restaurantScrollView);

                // Inflate the first box of the scroll view
                View restaurantView = inflater.inflate(R.layout.activity_rate_scroll_content,null);

                // Add the forms/content to the ScrollView
                sv.addView(restaurantView);

                // Display the view
                setContentView(v);

                //seekBar
                tasteSeekBar = (SeekBar) findViewById(R.id.seekbtn_taste);
                toppingSeekBar = (SeekBar) findViewById(R.id.seekbtn_toppings);
                bunSeekBar = (SeekBar) findViewById(R.id.seekbtn_bun);

                //Add the string to the banner
                TextView bannerBurgerFeed = (TextView)findViewById(R.id.et_banner);
                bannerBurgerFeed.setText(R.string.title_activity_burger_rating);
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
                burgerRatingButton.setBackgroundResource(R.mipmap.rate_button_on);
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

                tasteSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onStopTrackingTouch(SeekBar tasteSeekBar) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar tasteSeekBar) {

                    }

                    @Override
                    public void onProgressChanged(SeekBar tasteSeekBar, int progress, boolean fromUser) {
                        //tasteSeekBar.setProgress(0);
                        int increment = 1;
                        tasteSeekBar.setMax(8);
                        //tasteSeekBar.incrementProgressBy(1);
                        progress = ((int) Math.round(progress / increment)) * increment;
                        tasteSeekBar.setProgress(progress);
                        //taste.setText(progress + "");
                    }

                });

                toppingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onStopTrackingTouch(SeekBar toppingSeekBar) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar toppingSeekBar) {

                    }

                    @Override
                    public void onProgressChanged(SeekBar toppingSeekBar, int progress, boolean fromUser) {
                        //tasteSeekBar.setProgress(0);
                        int increment = 1;
                        toppingSeekBar.setMax(8);
                        //tasteSeekBar.incrementProgressBy(1);
                        progress = ((int)Math.round(progress/increment))*increment;
                        toppingSeekBar.setProgress(progress);
                        //taste.setText(progress + "");
                    }

                });

                bunSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onStopTrackingTouch(SeekBar bunSeekBar) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar bunSeekBar) {

                    }

                    @Override
                    public void onProgressChanged(SeekBar bunSeekBar, int progress, boolean fromUser) {
                        //tasteSeekBar.setProgress(0);
                        int increment = 1;
                        bunSeekBar.setMax(8);
                        //tasteSeekBar.incrementProgressBy(1);
                        progress = ((int)Math.round(progress/increment))*increment;
                        bunSeekBar.setProgress(progress);
                        //taste.setText(progress + "");
                    }

                });
    }

    public void onRatingSubmit(View view){
        //TODO:
        //pull data from views
        //construct burgerator backend class
        //get map from burgerator blackend class
        //pass map to custom request
        //execute custom request
    }

    public void onRatingSubmitResponse(JSONObject response){
        //TODO:print scuccess or fail dialog depending on status returned?
    }
}
