package burgerator.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.luis.burgerator.R;

/**
 * Created by Luis on 2/15/2016.
 */
public class SelectRestaurantActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Typeface eastwood = Typeface.createFromAsset(getAssets(), "fonts/Eastwood.ttf");

        /*This indented block should come before the onClick listeners before
        the onClick listeners wont trigger.*/
        // Adding custom elements to a ScrollView
       /* LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_rate_select_restaurant, null);

        // Find the ScrollView
        ScrollView sv = (ScrollView) v.findViewById(R.id.restaurant_view_scroll);

        // Inflate the first box of the scroll view
        View restaurantView = inflater.inflate(R.layout.activity_restaurant_view_scroll_content, null);

        // Add the forms/content to the ScrollView
        sv.addView(restaurantView); */


        // Display the view
        setContentView(R.layout.activity_rate_select_restaurant);

        //Add the string to the banner
        TextView bannerBurgerFeed = (TextView)findViewById(R.id.rest_banner);
        bannerBurgerFeed.setText(R.string.title_activity_select_restaurant);
        bannerBurgerFeed.setGravity(Gravity.CENTER);
        bannerBurgerFeed.setTypeface(eastwood);

        ImageButton back = (ImageButton)findViewById(R.id.imgbtn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RateActivity.class);
                startActivity(intent);
            }
        });


    }

}
