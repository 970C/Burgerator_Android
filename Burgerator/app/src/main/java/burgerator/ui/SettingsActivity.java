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
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.luis.burgerator.R;

/**
 * Created by Luis on 2/9/2016.
 */
public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Typeface eastwood = Typeface.createFromAsset(getAssets(), "fonts/Eastwood.ttf");

        /*This indented block should come before the onClick listeners before
        the onClick listeners wont trigger.*/
        // Adding custom elements to a ScrollView
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_settings, null);

        // Display the view
        setContentView(v);

        //Add the string to the banner
        TextView bannerBurgerFeed = (TextView)findViewById(R.id.setting_banner);
        bannerBurgerFeed.setText(getResources().getText(R.string.title_activity_setting));
        bannerBurgerFeed.setTextSize((float) 30.0);
        bannerBurgerFeed.setGravity(Gravity.CENTER);
        bannerBurgerFeed.setTypeface(eastwood);

        Button user = (Button)findViewById(R.id.user);
        user.setTypeface(eastwood);
        Button faceBook = (Button)findViewById(R.id.facebook);
        faceBook.setTypeface(eastwood);
        Button twitter = (Button)findViewById(R.id.twitter);
        twitter.setTypeface(eastwood);
        Button bug = (Button)findViewById(R.id.bug);
        bug.setTypeface(eastwood);
        Button contact = (Button)findViewById(R.id.contact_us);
        contact.setTypeface(eastwood);
        Button rate = (Button)findViewById(R.id.rate);
        rate.setTypeface(eastwood);
        Button logOut = (Button)findViewById(R.id.log_out);
        logOut.setTypeface(eastwood);

        ImageButton back = (ImageButton) findViewById(R.id.imgbtn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

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
        topBurgersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Top10Activity.class);
                startActivity(intent);
            }
        });

        // button to go to profile
        Button profileButton = (Button) findViewById(R.id.btn_profile_activity);
        profileButton.setBackgroundResource(R.mipmap.profile_button_on);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}

