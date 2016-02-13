package burgerator.yelp;

import org.json.JSONObject;

import java.util.List;

public interface YelpCallback{
    void onSuccess(JSONObject result);
    void onSuccess(List<JSONObject> result);
}