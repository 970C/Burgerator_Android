package burgerator.gms;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/*
*
 * Created by alec on 2/20/16.*/


public class LocationMarker {

    private List<MapPin> locs;


    public LocationMarker(){
        locs = new ArrayList<MapPin>();
    }


    public void generateMap(GoogleMap gMap, LatLngBounds pointBounds, float setZoom){
        Iterator itr = locs.iterator();

        while(itr.hasNext()){
            MapPin tmp = (MapPin) itr.next();
            gMap.addMarker(new MarkerOptions().position(new LatLng(tmp.getLat(), tmp.getLon())).title("Marker in " + tmp.getName()));

            LatLng coordinates = new LatLng(tmp.getLat(),tmp.getLon());

            //  int zoomValue = pointBounds.;
            //System.out.println("ZoomValue: " + zoomValue);
            gMap.moveCamera(CameraUpdateFactory.newLatLngBounds(pointBounds, 400, 400, 0));
            //gMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(<some lat & long>, setZoom)));
            //gMap.moveCamera(CameraUpdateFactory.zoomTo(setZoom));

            //newLatLngZoom(coordinates,));//newLatLngBounds(pointBounds,5,5,1));
        }
    }

    public void addLoc(MapPin r){
        locs.add(r);
    }

    public void addMultiLoc(List<MapPin> r){
        Iterator itr = r.iterator();

        while(itr.hasNext()) {
            locs.add((MapPin) itr.next());
        }
    }

    public void addLocString(String s){
        locs.add(new MapPin(s,0,0));
    }

}
