package burgerator.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luis.burgerator.R;

import org.json.JSONObject;

import burgerator.control.Controller;
import burgerator.db.BurgerDB;
import burgerator.util.ImageLoadTask;

public class ProfileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*This indented block should come before the onClick listeners before
        the onClick listeners wont trigger.*/
        // Adding custom elements to a ScrollView
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_profile, null);

        // Find the ScrollView
        //TODO: UNCOMMNET ScrollView sv = (ScrollView) v.findViewById(R.id.profileScrollView);

        // Inflate the first box of the scroll view
        ////TODO: UNCOMMNET View restaurantView = inflater.inflate(R.layout.activity_profile_scroll_content,null);

        // Add the forms/content to the ScrollView
        //TODO: UNCOMMNET sv.addView(restaurantView);

        // Display the view
        setContentView(v);

        //Add the string to the banner
        TextView bannerBurgerFeed = (TextView)findViewById(R.id.profile_banner);
        bannerBurgerFeed.setText(getResources().getText(R.string.title_activity_profile));
        bannerBurgerFeed.setTextSize((float)30.0);
        bannerBurgerFeed.setGravity(Gravity.CENTER);

        TextView userName = (TextView)findViewById(R.id.user_name);
        userName.setText(Controller.instance().getUser().getUserName());

        TextView userTitle = (TextView)findViewById(R.id.user_ranking);
        userTitle.setText(Controller.instance().getUser().getTitle());

        ImageView userPhoto = (ImageView)findViewById(R.id.imgv_user_image);
        String userPhotoUrl = Controller.instance().getUser().getPhoto();
        new ImageLoadTask(userPhotoUrl, userPhoto).execute();

        //dialog box
        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog userPhotoDialog = new Dialog(ProfileActivity.this);
                userPhotoDialog.setContentView(R.layout.user_profile_pic_dialog_box);
                userPhotoDialog.setCancelable(true);

                //set up text
                TextView dialogTitle = (TextView) userPhotoDialog.findViewById(R.id.burgerator_dialog_box_title);

                //set up buttons
                Button photoButton = (Button) userPhotoDialog.findViewById(R.id.imgbtn_open_photo);

                Button cameraButton = (Button) userPhotoDialog.findViewById(R.id.imgbtn_open_camera);

                //set up cancel button
                Button cancelButton = (Button) userPhotoDialog.findViewById(R.id.imgbtn_cancel);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

                userPhotoDialog.show();
            }
        });

        //settings button
        ImageButton settings = (ImageButton)findViewById(R.id.imgbtn_settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
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

        // Putting in an HTML Request to get recent rated burgers
        BurgerDB testRecentBurgers = new BurgerDB(getApplicationContext());
        //should return an object that contains:
        //      a list of burger objects, and the boolean to hasNextPage
        testRecentBurgers.getUserRatedBurgers(null, "harokevin@yahoo.com","1",
                new BurgerDB.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        onRecentBurgersResponse(response);
                    }
                });
    }

    public void onRecentBurgersResponse(JSONObject response){
        Log.d("Burgerator ProfileActivity","onRecentBurgersResponse(): " + response.toString());

        //TODO: populate views with burger data from response
    }
}
