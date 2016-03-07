package burgerator.ui;

import android.Manifest;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.luis.burgerator.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import burgerator.gms.GPSTracker;
import burgerator.gms.LocationDistances;
import burgerator.gms.LocationMarker;
import burgerator.gms.MapPin;

/**
 * Created by Luis on 2/28/2016.
 */
public class SearchActivityMapView extends FragmentActivity implements OnMapReadyCallback {

    private GPSTracker mLocation;
    //Parameters for security authorization permissions.
    private static final String[] INITIAL_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS
    };
    private static final String[] CAMERA_PERMS={
            Manifest.permission.CAMERA
    };
    private static final String[] CONTACTS_PERMS={
            Manifest.permission.READ_CONTACTS
    };
    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_map_view);

        Typeface eastwood = Typeface.createFromAsset(getAssets(), "fonts/Eastwood.ttf");


        mLocation = new GPSTracker(SearchActivityMapView.this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //Add the string to the banner
        TextView bannerBurgerFeed = (TextView) findViewById(R.id.profile_banner);
        bannerBurgerFeed.setText(R.string.find_a_burger);
        bannerBurgerFeed.setTextSize((float) 30.0);
        bannerBurgerFeed.setGravity(Gravity.CENTER);
        bannerBurgerFeed.setTypeface(eastwood);

        //list button
        ImageButton map = (ImageButton)findViewById(R.id.imgbtn_search);
        map.setBackgroundResource(R.drawable.list_view);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        // Initializes button views and their onClickListeners
        // button to go to findABurger
        Button findABurgerButton = (Button) findViewById(R.id.btn_search_activity);
        findABurgerButton.setBackgroundResource(R.mipmap.search_button_on);
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
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * On Map Ready, enter google map coordinates and title, receive markers of locations on google map.
     *
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {


        LocationMarker lm = new LocationMarker();
        List<MapPin> pins = new ArrayList<>();

        //dummy data: x and y coordinates and a title for each location
        String[] place = {"McDonalds", "WingCentral", "CampusUTotem", "SURC", "Jack in the Box"};
        double[] xcoords =  {46.980284, 46.981162, 47.000451, 47.002964, 47};
        double[] ycoords =   {-120.544566, -120.547410, -120.535812, -120.537797, -120.5445};

        //initialize dummy data arrays
        ArrayList<Double> xList = new ArrayList<>();
        ArrayList<Double> yList = new ArrayList<>();
        for(int i = 0; i<xcoords.length; i++){
            xList.add(i, xcoords[i]);
            yList.add(i, ycoords[i]);
        }

        List<JSONObject> restaurants = new ArrayList<>(); //fill with yelp data
        restaurants = Restaurants.instance().getList();
        List<String> restaurantName = new ArrayList<>();
        List<Double> restaurantXCoordinates = new ArrayList<>();
        List<Double> restaurantYCoordinates = new ArrayList<>();
        try {
            for (JSONObject restaurant : restaurants) {
                restaurantName.add(restaurant.getString("name"));
                restaurantXCoordinates.add(restaurant.getJSONObject("location").getJSONObject("coordinate").getDouble("latitude"));
                restaurantYCoordinates.add(restaurant.getJSONObject("location").getJSONObject("coordinate").getDouble("longitude"));
            }
        }catch(JSONException e){Log.d("SearchActivityMapView", e.getMessage());}

        /**
         *LocationDistance class is initialized and variables are set with
         * xList and yList. The farthestLatSort method is initialized and returns
         * box coordinates which is then condensed to a LatLngBounds instance.
         */
        //ALECS
        //LocationDistances ylocDis = new LocationDistances(yList);
        //LocationDistances xlocDis = new LocationDistances(xList);
        LocationDistances ylocDis = new LocationDistances(restaurantYCoordinates);
        LocationDistances xlocDis = new LocationDistances(restaurantXCoordinates);


            double[] xvalues = xlocDis.farthestLatSort(0);
            double east = xlocDis.eastGet();
            double west = xlocDis.westGet();

            double[] yvalues = ylocDis.farthestLatSort(1);
            double north = ylocDis.northGet();
            double south = ylocDis.southGet();

            LatLng ffirstPoint = new LatLng(xvalues[0], yvalues[0]);
            LatLng ssecondPoint = new LatLng(xvalues[1], yvalues[1]);

            LatLngBounds ppointBounds = new LatLngBounds(ffirstPoint, ssecondPoint);


        //initialize an array of MapPins
        for(int i = 0; i<place.length; i++){
            pins.add(i, new MapPin(restaurantName.get(i), restaurantXCoordinates.get(i), restaurantYCoordinates.get(i)));
        }

        lm.addMultiLoc(pins);


        mLocation = new GPSTracker(SearchActivityMapView.this);
        //lm.addLoc(new MapPin("Seattle", 47.6097, -122.3331));
        lm.addLoc(new MapPin("I AM HERE", mLocation.getLatitude(), mLocation.getLongitude()));



        /**
         * Return coordinates are then used to calculate the distance within the view, the view
         * is then set to the appropriate setting such that all coordinates are in reasonable view.
         */

        /*LocationDistances distCurrentLocation = new LocationDistances();

        for(int i = 0; i < xList.size(); i++) {

            double locDis = distCurrentLocation.distanceBetweenTwoPoints(mLocation.getLatitude(), xcoords[i], mLocation.getLongitude(), ycoords[i]);
            System.out.println("Distance to restruant: " + place[i] + " " + locDis + "miles");

        }

        System.out.println("east: " + east + "west: " + west + "north: " + north + "south: " + south);*/

        LocationDistances distView = new LocationDistances();
        double sSize = distView.distanceOfView(east, west, north, south);

        //System.out.println("Integer: " + distView.setZoom(sSize));

        lm.generateMap(googleMap, ppointBounds, distView.setZoom(sSize));
        //System.out.println("Distance is: " + sSize);
    }
}
