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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luis.burgerator.R;

import burgerator.control.Controller;

/**
 * Created by Luis on 2/9/2016.
 */
public class RankingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Typeface eastwood = Typeface.createFromAsset(getAssets(), "fonts/Eastwood.ttf");

        /*This indented block should come before the onClick listeners before
        the onClick listeners wont trigger.*/
        // Adding custom elements to a ScrollView
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_burger_rankings, null);

        // Display the view
        setContentView(v);

        //Add the string to the banner
        TextView bannerBurgerFeed = (TextView)findViewById(R.id.rank_banner);
        bannerBurgerFeed.setText(R.string.title_activity_ranking);
        bannerBurgerFeed.setGravity(Gravity.CENTER);
        bannerBurgerFeed.setTypeface(eastwood);

        //back button
        ImageButton back = (ImageButton)findViewById(R.id.imgbtn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        //badges
        String usersCurrentTitle = Controller.instance().getUser().getTitle();
        TextView rankText = (TextView)findViewById(R.id.current_rank);
        rankText.setText(usersCurrentTitle);
        rankText.setTypeface(eastwood);
        int usersCurrentBurgerCount = Controller.instance().getUser().getCount();
        int count = usersCurrentBurgerCount;
        for(int i=0; i < count; i++) {
            if(count <= 3) {
                ImageView squire = (ImageView) findViewById(R.id.img_burger_squire);
                squire.setImageResource(R.drawable.burger_squire_icon);

                TextView text = (TextView) findViewById(R.id.tv_burger_squire);
                text.setText("Burger Squire");

                ImageView temp = (ImageView)findViewById(R.id.img_burger_knight);
                temp.setImageResource(R.drawable.temp_rank_icon);

                ImageView logo = (ImageView)findViewById(R.id.imgv_user_ranking);
                logo.setImageResource(R.drawable.burger_squire_icon);
            } else if(count <= 8 && count > 3) {

                TextView text = (TextView) findViewById(R.id.tv_burger_knight);
                text.setText("Burger Knight");

                ImageView squire = (ImageView) findViewById(R.id.img_burger_squire);
                squire.setImageResource(R.drawable.burger_squire_icon);

                ImageView knight = (ImageView)findViewById(R.id.img_burger_knight);
                knight.setImageResource(R.drawable.burger_knight_icon);

                ImageView temp = (ImageView)findViewById(R.id.img_burger_baron);
                temp.setImageResource(R.drawable.temp_rank_icon);

                ImageView logo = (ImageView)findViewById(R.id.imgv_user_ranking);
                logo.setImageResource(R.drawable.burger_knight_icon);
            }else if(count <= 18 && count > 8) {
                TextView text = (TextView) findViewById(R.id.tv_burger_knight);
                text.setText("Burger Knight");

                ImageView squire = (ImageView) findViewById(R.id.img_burger_squire);
                squire.setImageResource(R.drawable.burger_squire_icon);

                ImageView knight = (ImageView)findViewById(R.id.img_burger_knight);
                knight.setImageResource(R.drawable.burger_knight_icon);

                ImageView baron = (ImageView)findViewById(R.id.img_burger_baron);
                baron.setImageResource(R.drawable.burger_baron_icon);

                ImageView temp = (ImageView)findViewById(R.id.img_burger_earl);
                temp.setImageResource(R.drawable.temp_rank_icon);

                ImageView logo = (ImageView)findViewById(R.id.imgv_user_ranking);
                logo.setImageResource(R.drawable.burger_baron_icon);
            }else if(count <= 26 && count > 18) {
                TextView text = (TextView) findViewById(R.id.tv_burger_knight);
                text.setText("Burger Earl");

                ImageView squire = (ImageView) findViewById(R.id.img_burger_squire);
                squire.setImageResource(R.drawable.burger_squire_icon);

                ImageView knight = (ImageView)findViewById(R.id.img_burger_knight);
                knight.setImageResource(R.drawable.burger_knight_icon);

                ImageView baron = (ImageView)findViewById(R.id.img_burger_baron);
                baron.setImageResource(R.drawable.burger_baron_icon);

                ImageView earl = (ImageView)findViewById(R.id.img_burger_earl);
                earl.setImageResource(R.drawable.burger_earl_icon);

                ImageView temp = (ImageView)findViewById(R.id.img_burger_marquis);
                temp.setImageResource(R.drawable.temp_rank_icon);

                ImageView logo = (ImageView)findViewById(R.id.imgv_user_ranking);
                logo.setImageResource(R.drawable.burger_earl_icon);
            }else if(count <= 40 && count > 26) {
                TextView text = (TextView) findViewById(R.id.tv_burger_knight);
                text.setText("Burger Marquis");

                ImageView squire = (ImageView) findViewById(R.id.img_burger_squire);
                squire.setImageResource(R.drawable.burger_squire_icon);

                ImageView knight = (ImageView)findViewById(R.id.img_burger_knight);
                knight.setImageResource(R.drawable.burger_knight_icon);

                ImageView baron = (ImageView)findViewById(R.id.img_burger_baron);
                baron.setImageResource(R.drawable.burger_baron_icon);

                ImageView earl = (ImageView)findViewById(R.id.img_burger_earl);
                earl.setImageResource(R.drawable.burger_earl_icon);

                ImageView marquis = (ImageView)findViewById(R.id.img_burger_marquis);
                marquis.setImageResource(R.drawable.burger_marquis_icon);

                ImageView temp = (ImageView)findViewById(R.id.img_burger_duke);
                temp.setImageResource(R.drawable.temp_rank_icon);

                ImageView logo = (ImageView)findViewById(R.id.imgv_user_ranking);
                logo.setImageResource(R.drawable.burger_marquis_icon);
            }else if(count <= 55 && count > 40) {
                TextView text = (TextView) findViewById(R.id.tv_burger_knight);
                text.setText("Burger Duke");

                ImageView squire = (ImageView) findViewById(R.id.img_burger_squire);
                squire.setImageResource(R.drawable.burger_squire_icon);

                ImageView knight = (ImageView)findViewById(R.id.img_burger_knight);
                knight.setImageResource(R.drawable.burger_knight_icon);

                ImageView baron = (ImageView)findViewById(R.id.img_burger_baron);
                baron.setImageResource(R.drawable.burger_baron_icon);

                ImageView earl = (ImageView)findViewById(R.id.img_burger_earl);
                earl.setImageResource(R.drawable.burger_earl_icon);

                ImageView marquis = (ImageView)findViewById(R.id.img_burger_marquis);
                marquis.setImageResource(R.drawable.burger_marquis_icon);

                ImageView duke = (ImageView)findViewById(R.id.img_burger_duke);
                duke.setImageResource(R.drawable.burger_duke_icon);

                ImageView temp = (ImageView)findViewById(R.id.img_burger_prince);
                temp.setImageResource(R.drawable.temp_rank_icon);

                ImageView logo = (ImageView)findViewById(R.id.imgv_user_ranking);
                logo.setImageResource(R.drawable.burger_duke_icon);
            }else if(count <= 74 && count > 55) {
                TextView text = (TextView) findViewById(R.id.tv_burger_knight);
                text.setText("Prince of Burgers");

                ImageView squire = (ImageView) findViewById(R.id.img_burger_squire);
                squire.setImageResource(R.drawable.burger_squire_icon);

                ImageView knight = (ImageView)findViewById(R.id.img_burger_knight);
                knight.setImageResource(R.drawable.burger_knight_icon);

                ImageView baron = (ImageView)findViewById(R.id.img_burger_baron);
                baron.setImageResource(R.drawable.burger_baron_icon);

                ImageView earl = (ImageView)findViewById(R.id.img_burger_earl);
                earl.setImageResource(R.drawable.burger_earl_icon);

                ImageView marquis = (ImageView)findViewById(R.id.img_burger_marquis);
                marquis.setImageResource(R.drawable.burger_marquis_icon);

                ImageView duke = (ImageView)findViewById(R.id.img_burger_duke);
                duke.setImageResource(R.drawable.burger_duke_icon);

                ImageView prince = (ImageView)findViewById(R.id.img_burger_prince);
                prince.setImageResource(R.drawable.prince_of_burgers_icon);

                ImageView temp = (ImageView)findViewById(R.id.img_burger_jake);
                temp.setImageResource(R.drawable.temp_rank_icon);

                ImageView logo = (ImageView)findViewById(R.id.imgv_user_ranking);
                logo.setImageResource(R.drawable.prince_of_burgers_icon);
            }else if(count <= 84 && count > 74) {
                TextView text = (TextView) findViewById(R.id.tv_burger_knight);
                text.setText("Burger Jake");

                ImageView squire = (ImageView) findViewById(R.id.img_burger_squire);
                squire.setImageResource(R.drawable.burger_squire_icon);

                ImageView knight = (ImageView)findViewById(R.id.img_burger_knight);
                knight.setImageResource(R.drawable.burger_knight_icon);

                ImageView baron = (ImageView)findViewById(R.id.img_burger_baron);
                baron.setImageResource(R.drawable.burger_baron_icon);

                ImageView earl = (ImageView)findViewById(R.id.img_burger_earl);
                earl.setImageResource(R.drawable.burger_earl_icon);

                ImageView marquis = (ImageView)findViewById(R.id.img_burger_marquis);
                marquis.setImageResource(R.drawable.burger_marquis_icon);

                ImageView duke = (ImageView)findViewById(R.id.img_burger_duke);
                duke.setImageResource(R.drawable.burger_duke_icon);

                ImageView prince = (ImageView)findViewById(R.id.img_burger_prince);
                prince.setImageResource(R.drawable.prince_of_burgers_icon);

                ImageView jake = (ImageView)findViewById(R.id.img_burger_jake);
                jake.setImageResource(R.drawable.burger_jake_icon);

                ImageView temp = (ImageView)findViewById(R.id.img_burger_king);
                temp.setImageResource(R.drawable.temp_rank_icon);

                ImageView logo = (ImageView)findViewById(R.id.imgv_user_ranking);
                logo.setImageResource(R.drawable.burger_jake_icon);
            }else if(count <= 100 && count > 84) {
                TextView text = (TextView) findViewById(R.id.tv_burger_knight);
                text.setText("King of Burgers");

                ImageView squire = (ImageView) findViewById(R.id.img_burger_squire);
                squire.setImageResource(R.drawable.burger_squire_icon);

                ImageView knight = (ImageView)findViewById(R.id.img_burger_knight);
                knight.setImageResource(R.drawable.burger_knight_icon);

                ImageView baron = (ImageView)findViewById(R.id.img_burger_baron);
                baron.setImageResource(R.drawable.burger_baron_icon);

                ImageView earl = (ImageView)findViewById(R.id.img_burger_earl);
                earl.setImageResource(R.drawable.burger_earl_icon);

                ImageView marquis = (ImageView)findViewById(R.id.img_burger_marquis);
                marquis.setImageResource(R.drawable.burger_marquis_icon);

                ImageView duke = (ImageView)findViewById(R.id.img_burger_duke);
                duke.setImageResource(R.drawable.burger_duke_icon);

                ImageView prince = (ImageView)findViewById(R.id.img_burger_prince);
                prince.setImageResource(R.drawable.prince_of_burgers_icon);

                ImageView jake = (ImageView)findViewById(R.id.img_burger_jake);
                jake.setImageResource(R.drawable.burger_jake_icon);

                ImageView king = (ImageView)findViewById(R.id.img_burger_king);
                king.setImageResource(R.drawable.king_of_burgers_icon);

                ImageView temp = (ImageView)findViewById(R.id.img_burger_emperor);
                temp.setImageResource(R.drawable.temp_rank_icon);

                ImageView logo = (ImageView)findViewById(R.id.imgv_user_ranking);
                logo.setImageResource(R.drawable.king_of_burgers_icon);
            }else if(count > 101) {
                TextView text = (TextView) findViewById(R.id.tv_burger_knight);
                text.setText("Burger Emperor");

                ImageView squire = (ImageView) findViewById(R.id.img_burger_squire);
                squire.setImageResource(R.drawable.burger_squire_icon);

                ImageView knight = (ImageView)findViewById(R.id.img_burger_knight);
                knight.setImageResource(R.drawable.burger_knight_icon);

                ImageView baron = (ImageView)findViewById(R.id.img_burger_baron);
                baron.setImageResource(R.drawable.burger_baron_icon);

                ImageView earl = (ImageView)findViewById(R.id.img_burger_earl);
                earl.setImageResource(R.drawable.burger_earl_icon);

                ImageView marquis = (ImageView)findViewById(R.id.img_burger_marquis);
                marquis.setImageResource(R.drawable.burger_marquis_icon);

                ImageView duke = (ImageView)findViewById(R.id.img_burger_duke);
                duke.setImageResource(R.drawable.burger_duke_icon);

                ImageView prince = (ImageView)findViewById(R.id.img_burger_prince);
                prince.setImageResource(R.drawable.prince_of_burgers_icon);

                ImageView jake = (ImageView)findViewById(R.id.img_burger_jake);
                jake.setImageResource(R.drawable.burger_jake_icon);

                ImageView king = (ImageView)findViewById(R.id.img_burger_king);
                king.setImageResource(R.drawable.king_of_burgers_icon);

                ImageView emperor = (ImageView)findViewById(R.id.img_burger_emperor);
                emperor.setImageResource(R.drawable.burger_emperor_icon);

                ImageView logo = (ImageView)findViewById(R.id.imgv_user_ranking);
                logo.setImageResource(R.drawable.burger_emperor_icon);
            }

        }
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
