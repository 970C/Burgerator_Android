package burgerator.ui;

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

import com.example.luis.burgerator.R;

import org.json.JSONException;
import org.json.JSONObject;

import burgerator.util.Burger;
import burgerator.db.BurgerDB;
import burgerator.util.BurgerFeed;
import burgerator.util.ImageLoadTask;

public class FeedActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*This indented block should come before the onClick listeners before
        the onClick listeners wont trigger.*/
        // Adding custom elements to a ScrollView
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_feed, null);

        // Find the ScrollView
        ScrollView sv = (ScrollView) v.findViewById(R.id.feedScrollView);

        // Inflate the first box of the scroll view
        View restaurantView = inflater.inflate(R.layout.activity_feed_scroll_content,null);

        // Add the forms/content to the ScrollView
        sv.addView(restaurantView);


        // Display the view
        setContentView(v);

        //Add the string to the banner
        TextView bannerBurgerFeed = (TextView)findViewById(R.id.et_banner);
        bannerBurgerFeed.setText(R.string.title_activity_burger_feed);
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
        final BurgerDB feedRequest = new BurgerDB(getApplicationContext());
        feedRequest.getBurgerFeed(null, "harokevin@yahoo.com", "", "",
                new BurgerDB.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        onFeedResponse(result);
                    }
                });

    }

    //called when the server returns the burger feed
    private void onFeedResponse(JSONObject response){
        Log.d("Burgerator FeedActivity","onFeedResponse(): " + response.toString());

        try {
            if(Integer.valueOf(response.getJSONObject("result").get("status").toString()) != 0){
                /////Initialize feed - populate views with burger data from response
                BurgerFeed.instance().setFeed(response);

                //Get current layout
                RelativeLayout feedElement = (RelativeLayout)findViewById(R.id.container0);

                //Get current burger
                Burger burger = BurgerFeed.instance().get(0);

                //Set user image/photo
                ImageView userPhoto = (ImageView) feedElement.findViewById(R.id.imgv_user_image);
                String userPhotoUrl = burger.getUserPhoto();
                new ImageLoadTask(userPhotoUrl, userPhoto).execute();

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

                //set number of pounds
                TextView pounds = (TextView) feedElement.findViewById(R.id.amount_burger_pounded);
                pounds.setText(burger.getPound());

                //TODO: add pound it picture to imgbtn_pound_it
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
