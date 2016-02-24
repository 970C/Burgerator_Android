package burgerator.util;

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

}
