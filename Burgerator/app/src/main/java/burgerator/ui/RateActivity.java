package burgerator.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
import java.nio.BufferUnderflowException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import burgerator.db.BurgerDB;
import burgerator.util.Burgerator;
import burgerator.util.User;

public class RateActivity extends Activity {

    //Seekbars for taste toppings and bun
    private SeekBar tasteSeekBar,toppingSeekBar,bunSeekBar;
    private String mRateTaste = "", mRateToppings = "", mRateBun = "";

    // HTTP request handler
    private BurgerDB mRequest;

    // result for taking a picture
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;

    // path for burger photo
    private String mBurgerPhotoPath;

    //Spinners - prep is for show; it is never taken into the db
    private Spinner mSpnrCheese,mSpnrRatio,mSpnrPrep;
    private String mSelectedCheese = "",mSelectedRatio = "",mSelcetedPrep = "";

    //WOULD YOU COME BACK FOR THE BURGER
    private RadioGroup wycbftb;
    private String mSelectedWycbftb = "";

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

        /*This indented block should come before the onClick listeners before
        the onClick listeners wont trigger.*/
        /* Setting up the activity's views */

                mRequest = new BurgerDB(getApplicationContext());

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

                //select restaurant to rate
                imgbtnLogo = (ImageButton) findViewById(R.id.imgv_restaurant_thumbnail);
                imgbtnLogo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SelectRestaurantActivity.class);
                        startActivity(intent);
                    }
                });

                btnSelectYourRestaurant = (Button) findViewById(R.id.et_restaurant_name);
                btnSelectYourRestaurant.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SelectRestaurantActivity.class);
                        startActivity(intent);
                    }
                });

                btnPickYourRestaurant = (Button) findViewById(R.id.et_pick_your_restaurant);
                btnPickYourRestaurant.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SelectRestaurantActivity.class);
                        startActivity(intent);
                    }
                });

                //seekbar intervals
                tasteSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar tasteSeekBar) {}
                    @Override
                    public void onStartTrackingTouch(SeekBar tasteSeekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar tasteSeekBar, int progress, boolean fromUser) {
                        int increment = 1;
                        tasteSeekBar.setMax(8);
                        progress = Math.round(progress / increment) * increment;
                        tasteSeekBar.setProgress(progress);

                        //Update value for burgerator object
                        mRateTaste = String.valueOf(progress);
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
                        toppingSeekBar.setMax(8);
                        progress = Math.round(progress/increment) *increment;
                        toppingSeekBar.setProgress(progress);

                        //Update value for burgerator object
                        mRateToppings = String.valueOf(progress);
                    }
                });

                bunSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar bunSeekBar) {}
                    @Override
                    public void onStartTrackingTouch(SeekBar bunSeekBar) {}

                    @Override
                    public void onProgressChanged(SeekBar bunSeekBar, int progress, boolean fromUser) {
                        int increment = 1;
                        bunSeekBar.setMax(8);
                        progress = ((int)Math.round(progress/increment))*increment;
                        bunSeekBar.setProgress(progress);

                        //Update value for burgerator object
                        mRateBun = String.valueOf(progress);
                    }
                });

                //TODO:Replace with proper activity
        /////SETTING UP SPINNERS
                
            ////SETTING UP CHEESE SPINNER
                Spinner mSpnrCheese = (Spinner) findViewById(R.id.spnr_cheese);
                // Spinner Drop down elements
                List<String> cheeses = new ArrayList <String>();
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

                // Creating adapter for spinner
                ArrayAdapter<String> cheeseAdapter = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item, cheeses);

                // Drop down layout style - list view with radio button
                cheeseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                mSpnrCheese.setAdapter(cheeseAdapter);
                mSpnrCheese.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // On selecting a spinner item
                        mSelectedCheese = parent.getItemAtPosition(position).toString();
                        String item = parent.getItemAtPosition(position).toString();
                    }
                    public void onNothingSelected(AdapterView<?> arg0) {}
                });

            ////SETTING UP RATIO SPINNER
                mSpnrRatio = (Spinner) findViewById(R.id.spnr_ratio);
                List<String> ratios = new ArrayList <String>();
                ratios.add(" ");
                ratios.add("bun heavy");
                ratios.add("balanced");
                ratios.add("meat heavy");
                // Creating adapter for spinner
                ArrayAdapter<String> ratioAdapter = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item, ratios);

                // Drop down layout style - list view with radio button
                ratioAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                mSpnrRatio.setAdapter(ratioAdapter);
                mSpnrRatio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // On selecting a spinner item
                        mSelectedRatio = parent.getItemAtPosition(position).toString();
                        String item = parent.getItemAtPosition(position).toString();
                    }
                    public void onNothingSelected(AdapterView<?> arg0) {}
                });
        
            ////SETTING UP PREP SPINNER
                mSpnrPrep = (Spinner) findViewById(R.id.spnr_prep);
                List<String> preps = new ArrayList <String>();
                preps.add("prep?");
                preps.add("under done");
                preps.add("just right");
                preps.add("over done");
                // Creating adapter for spinner
                ArrayAdapter<String> prepAdapter = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item, preps);

                // Drop down layout style - list view with radio button
                prepAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                mSpnrPrep.setAdapter(prepAdapter);
                mSpnrPrep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // On selecting a spinner item
                        mSelcetedPrep = parent.getItemAtPosition(position).toString();
                        String item = parent.getItemAtPosition(position).toString();
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });

        ////RADIO GROUP WYCBFTB
                wycbftb = (RadioGroup)findViewById(R.id.radgrp_wycbftb);
                wycbftb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if(checkedId == R.id.radbtn_thumbs_up){
                            mSelectedWycbftb = "10";    //Thumbs up is 10 in iOS db
                        }else if(checkedId == R.id.radbtn_thumbs_down){
                            mSelectedWycbftb = "0";     //Thumbs down is 0 in iOS db
                        }
                    }
                });

        ////Comments on Burger -> in method onRatingSubmit
        mComments = (EditText) findViewById(R.id.et_comments);
        mComments.setOnClickListener(new EditText.OnClickListener() {
            @Override
            public void onClick(View view) {
                mComments.setText("");
            }
        });
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
                Log.d("Burgerator Image", mBurgerPhotoPath );
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
        //TODO:
        //construct burgerator backend class
        Burgerator rating = new Burgerator();

            //populate burgerator with data from views
            rating.setVal("useremail", User.instance().getEmail());

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
            rating.setVal("restaurantImageUrl","");
            rating.setVal("restaurantAddress","");
            rating.setVal("restaurantCity","");

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

            mRequest.rate(rating.getBurgerMap(), mBurgerPhotoPath, new BurgerDB.VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    Log.d("Burgerator Rate Response", response.toString());
                    onRatingSubmitResponse(response);
                }
            });
        }catch(Exception e){
            Log.d("Burgerator BurgerDB.Rate Catch",e.toString());
        }

        Toast.makeText(getApplicationContext(),
                "Uploading Burger...",
                Toast.LENGTH_LONG).show();
    }

    public void onRatingSubmitResponse(JSONObject response) {
        //TODO:print scuccess or fail dialog depending on status returned?
        Log.d("Burgerator Rate Response", response.toString());


        String toastMessage = "default/null";
        try{
            if (response.getJSONObject("result").getInt("status") == 1) {
                toastMessage = "Burger Successfully Rated!";
            }else{
                toastMessage = "Burger Not Successfullt Rated! :(";
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
