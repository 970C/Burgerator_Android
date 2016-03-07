package burgerator.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.*;

/**
 * Created by Jonathan on 2/20/2016.
 * session data for restaurants
 */
public class Restaurants {

    List<JSONObject> r;
    private static final Restaurants RESTAURANTS = new Restaurants();

    private Restaurants(){
        r = new ArrayList<>();
    }

    public static Restaurants instance(){
        return RESTAURANTS;
    }

    public void addList(List<JSONObject> _r){
        r = _r;
    }

    public List getList(){
        return r;
    }

    public List getCoordsList(){
        JSONObject json;
        Iterator<JSONObject> itr = r.iterator();
        while (itr.hasNext()){
            try {
                Log.d("rest.getCoords", itr.next().getJSONObject("location").getJSONObject("coordinate").toString());
            }catch(JSONException e){
                Log.d("rest.getCoords", e.getMessage());
            }
        }
        return new ArrayList();
    }

}
