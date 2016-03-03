package burgerator.gms;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;
import com.example.luis.burgerator.R;

import burgerator.ui.SearchActivityMapView;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

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

/*
*
     * set up location, and
     * setContentView method to the current activity.
     * Let the map be created within a map fragment.
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mLocation = new GPSTracker(MapsActivity.this);

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

/**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * On Map Ready, enter google map coordinates and title, receive markers of locations on google map.
     *
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.*/


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

         /**LocationDistance class is initialized and variables are set with
         * xList and yList. The farthestLatSort method is initialized and returns
         * box coordinates which is then condensed to a LatLngBounds instance.*/


                LocationDistances ylocDis = new LocationDistances(yList);
                LocationDistances xlocDis = new LocationDistances(xList);

                double[] xvalues = xlocDis.farthestLatSort(0);
                double east = xlocDis.eastGet();
                double west = xlocDis.westGet();

                double[] yvalues = ylocDis.farthestLatSort(1);
                double north =  ylocDis.northGet();
                double south = ylocDis.southGet();

                LatLng ffirstPoint = new LatLng(xvalues[0], yvalues[0]);
                LatLng ssecondPoint = new LatLng(xvalues[1], yvalues[1]);

                LatLngBounds ppointBounds = new LatLngBounds(ffirstPoint, ssecondPoint);

        //initialize an array of MapPins
        for(int i = 0; i<place.length; i++){
            pins.add(i, new MapPin(place[i], xcoords[i], ycoords[i]));
        }

        lm.addMultiLoc(pins);


        mLocation = new GPSTracker(MapsActivity.this);
        lm.addLoc(new MapPin("Seattle", 47.6097, -122.3331));
        lm.addLoc(new MapPin("I AM HERE", mLocation.getLatitude(),mLocation.getLongitude() ));



/**
         * Return coordinates are then used to calculate the distance within the view, the view
         * is then set to the appropriate setting such that all coordinates are in reasonable view.*/



        LocationDistances distCurrentLocation = new LocationDistances();

        for(int i = 0; i < xList.size(); i++) {

            double locDis = distCurrentLocation.distanceBetweenTwoPoints(mLocation.getLatitude(), xcoords[i], mLocation.getLongitude(), ycoords[i]);
            System.out.println("Distance to restruant: " + place[i] + " " + locDis + "miles");

        }

        System.out.println("east: " + east + "west: " + west + "north: "+ north  + "south: " + south);

        LocationDistances distView = new LocationDistances();
        double sSize = distView.distanceOfView(east, west, north, south);

        System.out.println("Integer: " + distView.setZoom(sSize));

        lm.generateMap(googleMap, ppointBounds, distView.setZoom(sSize));
        System.out.println("Distance is: " + sSize);
    }

}

