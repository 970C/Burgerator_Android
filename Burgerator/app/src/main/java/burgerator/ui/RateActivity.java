package burgerator.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luis.burgerator.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import burgerator.control.Controller;
import burgerator.db.BurgerDB;
import burgerator.util.Burgerator;
import burgerator.util.User;

public class RateActivity extends Activity {

    //Seekbars for taste toppings and bun
    private SeekBar tasteSeekBar,toppingSeekBar,bunSeekBar;
    private String mRateTaste, mRateToppings, mRateBun;
    private TextView tasteDescription, bunDescription, toppingsDescription;

    // HTTP request handler
    private BurgerDB mRequest;

    // result for taking a picture
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GET_CHEESE = 2;
    static final int REQUEST_GET_RESTAURANT = 3;
    static final int REQUEST_GET_BURGER = 4;


    // path for burger photo
    private String mBurgerPhotoPath;

    //Spinners - prep is for show; it is never taken into the db
    private Button mSpnrCheese,mSpnrRatio,mSpnrPrep;
    private String mSelectedCheese = "",mSelectedRatio = "",mSelcetedPrep = "";

    //WOULD YOU COME BACK FOR THE BURGER
    private RadioGroup wycbftb;
    private String mSelectedWycbftb = "";
    private RadioButton up;
    private RadioButton down;

    //Comments on burgers
    private EditText mComments;
    String mWritenComments = "";

    //select restaurant buttons
    private ImageButton imgbtnLogo;
    private Button btnPickYourRestaurant;
    private Button btnSelectYourRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //font
        final Typeface eastwood = Typeface.createFromAsset(getAssets(), "fonts/Eastwood.ttf");

        /*This indented block should come before the onClick listeners before
        the onClick listeners wont trigger.*/
        /* Setting up the activity's views */

                mRequest = new BurgerDB(getApplicationContext());

                // Adding custom elements to a ScrollView
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.activity_rate, null);
                /*FontsOverride.setDefaultFont(getApplicationContext(), "DEFAULT", "fonts/Eastwood.ttf");
                FontsOverride.setDefaultFont(getApplicationContext(), "MONOSPACE", "fonts/Eastwood.ttf");
                FontsOverride.setDefaultFont(getApplicationContext(), "SERIF", "fonts/Eastwood.ttf");
                FontsOverride.setDefaultFont(getApplicationContext(), "SANS_SERIF", "fonts/Eastwood.ttf");*/

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
                toppingSeekBar = (SeekBar) findViewById(R.id.seekbtn_topping);
                bunSeekBar = (SeekBar) findViewById(R.id.seekbtn_bun);

                //text description
                TextView taste = (TextView)findViewById(R.id.tv_taste);
                taste.setTypeface(eastwood);
                tasteDescription = (TextView)findViewById(R.id.taste_description);
                tasteDescription.setTypeface(eastwood);
                TextView toppings = (TextView)findViewById(R.id.tv_toppings);
                toppings.setTypeface(eastwood);
                toppingsDescription = (TextView)findViewById(R.id.toppings_description);
                toppingsDescription.setTypeface(eastwood);
                TextView description = (TextView)findViewById(R.id.tv_bun);
                description.setTypeface(eastwood);
                bunDescription = (TextView)findViewById(R.id.bun_description);
                bunDescription.setTypeface(eastwood);

                //Add the string to the banner
                TextView bannerBurgerFeed = (TextView)findViewById(R.id.et_banner);
                bannerBurgerFeed.setText(R.string.title_activity_burger_rating);
                bannerBurgerFeed.setTextSize((float) 30.0);
                bannerBurgerFeed.setGravity(Gravity.CENTER);
                bannerBurgerFeed.setTypeface(eastwood);

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
                final Button profileButton = (Button) findViewById(R.id.btn_profile_activity);
                profileButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent);
                    }
                });

                //select restaurant to rate
                //TODO: condense to one button
                        imgbtnLogo = (ImageButton) findViewById(R.id.imgv_restaurant_thumbnail);
                        imgbtnLogo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {onRestaurantSelect();}
                        });
                        btnSelectYourRestaurant = (Button) findViewById(R.id.et_restaurant_name);
                        btnSelectYourRestaurant.setTypeface(eastwood);
                        btnSelectYourRestaurant.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {onRestaurantSelect();}
                        });
                        btnPickYourRestaurant = (Button) findViewById(R.id.et_pick_your_restaurant);
                        btnPickYourRestaurant.setTypeface(eastwood);
                        btnPickYourRestaurant.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {onRestaurantSelect();}
                        });

                //seekbar intervals
                tasteSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar tasteSeekBar) {
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar tasteSeekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar tasteSeekBar, int progress, boolean fromUser) {
                        int increment = 1;
                        tasteSeekBar.setMax(10);
                        progress = Math.round(progress / increment) * increment;
                        tasteSeekBar.setProgress(progress);
                        mRateTaste = String.valueOf(progress);

                        //Update value for burgerator object
                        //tasteDescription.setText("" + progress);
                        switch (progress) {
                            case 1: tasteDescription.setText("rather eat dirt  " + progress);
                                break;
                            case 2: tasteDescription.setText("very bad  " + progress);
                                break;
                            case 3: tasteDescription.setText("doesn't do it  " + progress);
                                break;
                            case 4: tasteDescription.setText("sub-par  " + progress);
                                break;
                            case 5: tasteDescription.setText("just awsome  " + progress);
                                break;
                            case 6: tasteDescription.setText("pretty good  " + progress);
                                break;
                            case 7: tasteDescription.setText("darn good  " + progress);
                                break;
                            case 8: tasteDescription.setText("grade 'a'  " + progress);
                                break;
                            case 9: tasteDescription.setText("mouth party  " + progress);
                                break;
                            case 10: tasteDescription.setText("religious  " + progress);
                                break;
                            default: tasteDescription.setText(" ");
                                break;
                        }
                    }
                });

                toppingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar toppingSeekBar) {}
                    @Override
                    public void onStartTrackingTouch(SeekBar toppingSeekBar) {}

                    @Override
                    public void onProgressChanged(SeekBar toppingSeekBar, int progress, boolean fromUser) {
                        int increment = 1;
                        toppingSeekBar.setMax(10);
                        progress = Math.round(progress/increment) *increment;
                        toppingSeekBar.setProgress(progress);
                        mRateToppings = String.valueOf(progress);

                        //Update value for burgerator object
                        //toppingsDescription.setText(" "+ progress);
                        switch (progress) {
                            case 1: toppingsDescription.setText("rotten  " + progress);
                                break;
                            case 2: toppingsDescription.setText("gross  " + progress);
                                break;
                            case 3: toppingsDescription.setText("missed it  " + progress);
                                break;
                            case 4: toppingsDescription.setText("nothin' special  " + progress);
                                break;
                            case 5: toppingsDescription.setText("same old  " + progress);
                                break;
                            case 6: toppingsDescription.setText("fresh  " + progress);
                                break;
                            case 7: toppingsDescription.setText("tasty  " + progress);
                                break;
                            case 8: toppingsDescription.setText("awsome  " +progress);
                                break;
                            case 9: toppingsDescription.setText("next level  " + progress);
                                break;
                            case 10: toppingsDescription.setText("mind blowing  " + progress);
                                break;
                            default: toppingsDescription.setText("meat & bun  " );
                                break;

                        }
                    }
                });

                bunSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar bunSeekBar) {}
                    @Override
                    public void onStartTrackingTouch(SeekBar bunSeekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar bunSeekBar, int progress, boolean fromUser) {
                        int increment = 1;
                        bunSeekBar.setMax(10);
                        progress = ((int)Math.round(progress/increment))*increment;
                        bunSeekBar.setProgress(progress);
                        mRateBun = String.valueOf(progress);

                        //Update value for burgerator object
                        //bunDescription.setText("" + progress);
                        switch (progress) {
                            case 1: bunDescription.setText("fail  " +progress);
                                break;
                            case 2: bunDescription.setText("just ain't right  "+ progress);
                                break;
                            case 3: bunDescription.setText("bad fit  " + progress);
                                break;
                            case 4: bunDescription.setText("not quite right  " + progress);
                                break;
                            case 5: bunDescription.setText("standard  " + progress);
                                break;
                            case 6: bunDescription.setText("pretty good  " + progress);
                                break;
                            case 7: bunDescription.setText("great bun  " + progress);
                                break;
                            case 8: bunDescription.setText("love it..  " + progress);
                                break;
                            case 9: bunDescription.setText("like a glove  " + progress);
                                break;
                            case 10: bunDescription.setText("yoga pants  " + progress);
                                break;
                            default: bunDescription.setText("went bunless  ");
                                break;
                        }
                    }
                });

                //TODO:Replace with proper activity
        /////SETTING UP SPINNERS
                
            ////SETTING UP CHEESE SPINNER
                mSpnrCheese = (Button) findViewById(R.id.spnr_cheese);
                mSpnrCheese.setTypeface(eastwood);
                mSpnrCheese.setText(R.string.cheese_button);
                mSpnrCheese.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), CheeseActivity.class);
                        startActivityForResult(intent, REQUEST_GET_CHEESE);
                    }
                });


            ////SETTING UP RATIO SPINNER
                mSpnrRatio = (Button) findViewById(R.id.spnr_ratio);
                mSpnrRatio.setTypeface(eastwood);
                mSpnrRatio.setText(R.string.ratio_button);
                mSpnrRatio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog ratio = new Dialog(RateActivity.this);
                        ratio.setContentView(R.layout.ratio_popover);

                        Button bunHeavy = (Button) ratio.findViewById(R.id.img_bun_heavy);
                        bunHeavy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mSpnrRatio.setTypeface(eastwood);
                                mSpnrRatio.setText(R.string.bun_heavy);
                                mSelectedRatio = "bun heavy";
                                ratio.dismiss();
                            }
                        });

                        Button balanced = (Button) ratio.findViewById(R.id.img_balanced);
                        balanced.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mSpnrRatio.setTypeface(eastwood);
                                mSpnrRatio.setText(R.string.balanced);
                                mSelectedRatio = "balanced";
                                ratio.dismiss();
                            }
                        });

                        Button meatHeavy = (Button) ratio.findViewById(R.id.img_meat_heavy);
                        meatHeavy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mSpnrRatio.setTypeface(eastwood);
                                mSpnrRatio.setText(R.string.meat_heavy);
                                mSelectedRatio = "meat heavy";
                                ratio.dismiss();
                            }
                        });

                        ratio.setCanceledOnTouchOutside(true);
                        ratio.show();

                    }
                });


        ////RADIO GROUP WYCBFTB and text
                TextView wycbftbText = (TextView)findViewById(R.id.tv_come_back);
                wycbftbText.setTypeface(eastwood);
                wycbftb = (RadioGroup)findViewById(R.id.radgrp_wycbftb);
                up = (RadioButton)findViewById(R.id.radbtn_thumbs_up);
                down = (RadioButton)findViewById(R.id.radbtn_thumbs_down);
                wycbftb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == R.id.radbtn_thumbs_up) {
                            mSelectedWycbftb = "10";    //Thumbs up is 10 in iOS db
                            up.setButtonDrawable(R.drawable.thumbs_up_red);
                            down.setButtonDrawable(R.drawable.thumbs_down_black);
                        } else if (checkedId == R.id.radbtn_thumbs_down) {
                            mSelectedWycbftb = "0";     //Thumbs down is 0 in iOS db
                            down.setButtonDrawable(R.drawable.thumbs_down_red);
                            up.setButtonDrawable(R.drawable.thumbs_up_black);
                        }
                    }
                });

        ////Comments on Burger -> in method onRatingSubmit
        mComments = (EditText) findViewById(R.id.et_comments);
        mComments.setTypeface(eastwood);
        mComments.setOnClickListener(new EditText.OnClickListener() {
            @Override
            public void onClick(View view) {
                mComments.setTypeface(eastwood);
                mComments.setText("");
            }
        });

        Button submit = (Button)findViewById(R.id.btn_submit);
        submit.setTypeface(eastwood);
    }

    public void onTakePicture(View view){

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.d("burgerator", ex.toString());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                Log.d("Burgerator Image", mBurgerPhotoPath);
            }
        }
    }

    /**
     * Method that acts as the response to the user clicking one
     * of the three select restaurant buttons:
     * imgbtnLogo, btnSelectYourRestaurant, btnPickYourRestaurant
     * Sends the user to SelectRestaurantActivity.class
     */
    public void onRestaurantSelect(){
        //TODO: uncomment and complete
        //Intent intent = new Intent(getApplicationContext(), SelectRestaurantActivity.class);
        //startActivityForResult(intent,REQUEST_GET_RESTAURANT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final Typeface eastwood = Typeface.createFromAsset(getAssets(), "fonts/Eastwood.ttf");

        //When the user is done taking the photo
        if(requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
            //Setting the image button's image to the photo taken by the user

                    //Initialize image button view and bitmap that will represent the image
                    ImageButton burgerImage = (ImageButton)findViewById(R.id.imgbtn_snapshot);
                    Bitmap bmBurgerImage = BitmapFactory.decodeFile(mBurgerPhotoPath);

                    //Determine the orientation and rotate the bitmap accordingly
                    //This is done so the image is upright when displayed in the activity
                    try {
                        ExifInterface exif = new ExifInterface(mBurgerPhotoPath);
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

                        Log.d("Burgerator Image", "Exif: " + orientation);

                        Matrix matrix = new Matrix();
                        if (orientation == 6) {
                            matrix.postRotate(90);
                        }
                        else if (orientation == 3) {
                            matrix.postRotate(180);
                        }
                        else if (orientation == 8) {
                            matrix.postRotate(270);
                        }
                        bmBurgerImage = Bitmap.createBitmap(bmBurgerImage,
                                                            0, 0, bmBurgerImage.getWidth(),
                                                            bmBurgerImage.getHeight(),
                                                            matrix, true); // rotating bitmap
                    }
                    catch (Exception e) {
                        Log.d("Burgerator Image", e.toString());
                    }

                    //Set the image
                    burgerImage.setImageBitmap(bmBurgerImage);
        }

        //get cheese string from string activity
        if(requestCode == REQUEST_GET_CHEESE && resultCode == RESULT_OK) {
            if(resultCode == Activity.RESULT_OK) {
                mSpnrCheese.setTypeface(eastwood);
                mSpnrCheese.setText(data.getStringExtra("result"));
                mSelectedCheese = data.getStringExtra("result");

            }
        }

        //get cheese string from string activity
        if(requestCode == REQUEST_GET_RESTAURANT && resultCode == RESULT_OK) {
            mSpnrCheese.setTypeface(eastwood);
            mSpnrCheese.setText(data.getStringExtra("result"));
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mBurgerPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void onRatingSubmit(View view){
        //construct burgerator backend class
        Burgerator rating = new Burgerator();

            //populate burgerator with data from views
            rating.setVal("useremail", Controller.instance().getUser().getEmail());

            //set cheese and ratio - no where to put prep in db
            rating.setVal("cheese",mSelectedCheese);
            rating.setVal("ratio", mSelectedRatio);

            //set taste, toppings, bun
            rating.setVal("taste", mRateTaste);
            rating.setVal("toppings", mRateToppings);
            rating.setVal("bunrate", mRateBun);

            //set wycbftb
            rating.setVal("wycbftb",mSelectedWycbftb);

            //set comment
            mComments = (EditText)findViewById(R.id.et_comments);
            mWritenComments = mComments.getText().toString();
            rating.setVal("comment",mWritenComments);

            //TODO: get information from yelp api
            rating.setVal("restaurantId", "5"); //test value
            rating.setVal("burgerName","Test Post Toast Burger"); //test value
            rating.setVal("restaurantName","Testaurant"); //test value
            rating.setVal("restaurantZip","98909");
            rating.setVal("restaurantImageUrl","kh");
            rating.setVal("restaurantAddress","kh");
            rating.setVal("restaurantCity","kh");

            //Making these test values not be null beacuse of lots of null point exceptions
            rating.setVal("fries","0");         //0 by default in iOS db
            rating.setVal("size","0");              //
            rating.setVal("bun","0");               //
            rating.setVal("price","0");             //
            rating.setVal("ambience","0");          //
            rating.setVal("friesrate","0");         //
            rating.setVal("presentation","0");      //
            rating.setVal("sauces","0");            //
            rating.setVal("juicyness","0");         //
            rating.setVal("service","0");           //

            rating.setVal("donenessRequested","20");    //Majority of iOS db entires are 20
            rating.setVal("donenessReceived","0");      //Majority of iOS db entires are 0

            rating.setVal("image","");      //image added at request level
            rating.setVal("imageUrl","");   //handled by db
            rating.setVal("date","");       //handled by db


        try {
            //get map from burgerator blackend class
            //pass map to BurgerDB
            if(rating.validate(getApplicationContext(), mBurgerPhotoPath)){

                Toast.makeText(getApplicationContext(),"Uploading Burger...", Toast.LENGTH_LONG).show();

                mRequest.rate(rating.getBurgerMap(), mBurgerPhotoPath, new BurgerDB.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        Log.d("Burgerator Rate Response", response.toString());
                        onRatingSubmitResponse(response);
                    }
                });
            }else{
                //toast generated in rating
            }
        }catch(Exception e){
            Log.d("Burgerator BurgerDB.Rate Catch",e.toString());
        }
    }

    public void onRatingSubmitResponse(JSONObject response) {
        //TODO:print scuccess or fail dialog depending on status returned?
        Log.d("Burgerator Rate Response", response.toString());


        String toastMessage = "default/null";
        try{
            if (response.getJSONObject("result").getInt("status") == 1) {
                toastMessage = "Burger Successfully Rated!";
            }else{
                toastMessage = response.getJSONObject("result").
                                getJSONObject("error").getString("message");
            }
        }catch(JSONException e){
            toastMessage = "no status key in the response";
        }

        Toast.makeText(getApplicationContext(),
                        toastMessage,
                        Toast.LENGTH_SHORT).show();

        //TODO:clear forms

    }
}
