package burgerator.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.luis.burgerator.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luis on 2/28/2016.
 */
public class CheeseActivity extends Activity {

    private String mSelectedCheese = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*This indented block should come before the onClick listeners before
        the onClick listeners wont trigger.*/
        // Adding custom elements to a ScrollView
        /*LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_cheese, null);

        // Find the ScrollView
        ScrollView sv = (ScrollView) v.findViewById(R.id.select_cheese);*/

        // Display the view
        setContentView(R.layout.activity_cheese);

        //Add the string to the banner
        TextView bannerBurgerFeed = (TextView)findViewById(R.id.rank_banner);
        bannerBurgerFeed.setText(R.string.title_activity_cheese);
        bannerBurgerFeed.setTextSize((float) 30.0);
        bannerBurgerFeed.setGravity(Gravity.CENTER);

        //back button
        ImageButton back = (ImageButton)findViewById(R.id.imgbtn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RateActivity.class);
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
        burgerFeedButton.setBackgroundResource(R.mipmap.rate_button_on);
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

        List<String> cheeses = new ArrayList<String>();
        cheeses.add(" ");
        cheeses.add("no cheese");
        cheeses.add("american");
        cheeses.add("cheddar");
        cheeses.add("pepper jack");
        cheeses.add("blue");
        cheeses.add("swiss");
        cheeses.add("gouda");
        cheeses.add("other");
        cheeses.add("provolone");
        cheeses.add("goat");
        cheeses.add("mozzarella");
        cheeses.add("monterey jack");

    }
}
