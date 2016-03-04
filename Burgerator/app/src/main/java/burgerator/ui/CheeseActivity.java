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
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luis.burgerator.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luis on 2/28/2016.
 */
public class CheeseActivity extends Activity {

    private String mSelectedCheese = "";
    private ListView cheese;
    private ArrayAdapter<String> listAdapter;
    private String object;
    private TextView mTextView;

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

        cheese = (ListView) findViewById(R.id.cheese_selection);

        ArrayList<String> cheeseList = new ArrayList<String>();
        cheeseList.add("no cheese");
        cheeseList.add("american");
        cheeseList.add("cheddar");
        cheeseList.add("pepper jack");
        cheeseList.add("blue");
        cheeseList.add("swiss");
        cheeseList.add("gouda");
        cheeseList.add("other");
        cheeseList.add("provolone");
        cheeseList.add("goat");
        cheeseList.add("mozzarella");
        cheeseList.add("monterey jack");

        listAdapter = new ArrayAdapter<String>(this, R.layout.cheese_text_view,R.id.cheese_text_view, cheeseList);

        cheese.setAdapter(listAdapter);

        cheese.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                object = (String) cheese.getItemAtPosition(position);
            }
        });

        //selected cheese text box
        mTextView = (TextView)findViewById(R.id.cheese_selected);
        mTextView.setText(object);

        Button submit = (Button)findViewById(R.id.submit_cheese);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("result", object);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
