package burgerator.util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.luis.burgerator.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Acts as an adapter for the ListView in SearchActivity.
 * Uses Layout: R.layout.activity_search_result_container
 */
public class SearchAdapter extends ArrayAdapter{

    private Context mContext;
    private LayoutInflater mInflater;

    private List mRestaurants;

    /**
     * Initializes: context and inflator using context and list of restaurants using restaurants.
     * @param context context from main view necessary to inflate ListView elements
     * @param restaurants List of restaurants to take data from
     */
    public SearchAdapter(Context context, List restaurants) {
        super(context, R.layout.activity_search_result_container, restaurants);

        mContext = context;
        mInflater = LayoutInflater.from(context);

        if(restaurants == null){
            mRestaurants = new ArrayList<JSONObject>();
        }else{
            mRestaurants = restaurants;
        }
    }

    /**
     * Overridden method that is used to compose the Top10Activity ListView
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Create parent to XML
        RelativeLayout container = new RelativeLayout(getContext());

        //Inflate XML to use in java
        if(convertView == null){
            mInflater.inflate(R.layout.activity_search_result_container,container,true);
        }else {
            container = (RelativeLayout) convertView;
        }

        //Get containers' views
        ImageView restrauntImg = (ImageView)container.findViewById(R.id.imgv_restaurant_thumbnail);
        TextView    distanceTxt = (TextView)container.findViewById(R.id.tv_distance);
        TextView    restaurantNameTxt = (TextView)container.findViewById(R.id.tv_restaurant_name);
        TextView    restaurantAddrTxt = (TextView)container.findViewById(R.id.restaurant_address);
        TextView    ratingTxt = (TextView)container.findViewById(R.id.rating_value);

        //Check if burger feed in empty
        if(mRestaurants == null || mRestaurants.size() == 0){
            //Set dummy content
            Glide.with(mContext).load(R.mipmap.app_icon).into(restrauntImg);
            restaurantNameTxt.setText("Loading...");

        }else{
            //Set real content

            //Set content for containers' views
            String restrauntImgUrl = "";
            try {
                restrauntImgUrl = (String)((JSONObject)mRestaurants.get(position)).get("image_url");
                Glide.with(mContext).load(restrauntImgUrl).into(restrauntImg);
                //TODO: set actual restaurant data
            } catch (JSONException e) {
                Log.e("SearchAdapter getView", e.toString());
            }
        }
        return container;
    }


}
