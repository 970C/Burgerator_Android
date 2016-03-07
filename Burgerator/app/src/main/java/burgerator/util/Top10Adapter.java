package burgerator.util;

import android.content.Context;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Acts as an adapter for the ListView in Top10Activity.
 * Uses Layout: R.layout.activity_top10_burger_feed_container
 */
public class Top10Adapter extends ArrayAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private List<Burger> mBurgers;

    /**
     * Initializes: context and inflator using context and list of burgers using burgers.
     * @param context context from main view necessary to inflate ListView elements
     * @param burgers List of burgers to take data from
     */
    public Top10Adapter(Context context, List<Burger> burgers) {
        super(context, R.layout.activity_top10_burger_feed_container,burgers);

        mContext = context;
        mInflater = LayoutInflater.from(context);

        if(burgers == null){
            mBurgers = new ArrayList<Burger>();
        }else{
            mBurgers = burgers;
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
        //Used by listener
        final int pos = position;

        //Create parent to XML
        RelativeLayout container = new RelativeLayout(getContext());

        //Inflate XML to use in java
        if(convertView == null){
            mInflater.inflate(R.layout.activity_top10_burger_feed_container,container,true);
        }else {
            container = (RelativeLayout) convertView;
        }

        //Get containers' views
        ImageButton burgerImg = (ImageButton)container.findViewById(R.id.imgv_burger_picture);
        ImageView   rankImg   = (ImageView)container.findViewById(R.id.imgv_burger_ranking);
        TextView    burgerTxt = (TextView)container.findViewById(R.id.burger_name);
        TextView    restaurantTxt = (TextView)container.findViewById(R.id.restaurant_name);
        TextView    restaurantAddrTxt = (TextView)container.findViewById(R.id.restaurant_address);
        TextView    ratingTxt = (TextView)container.findViewById(R.id.rating_value);

        //Check if burger feed in empty
        if(mBurgers == null || mBurgers.size() == 0){
            //Set dummy content
            Glide.with(mContext).load(R.mipmap.app_icon).into(burgerImg);
            Glide.with(mContext).load(R.mipmap.app_icon).into(rankImg);
            restaurantTxt.setText("Loading...");

        }else{
            //Set real content

            //TODO: Set rank image/photo
            int rank = R.drawable.rank10;
            switch (position){
                case 0: rank = R.drawable.rank1;break;
                case 1: rank = R.drawable.rank2;break;
                case 2: rank = R.drawable.rank3;break;
                case 3: rank = R.drawable.rank4;break;
                case 4: rank = R.drawable.rank5;break;
                case 5: rank = R.drawable.rank6;break;
                case 6: rank = R.drawable.rank7;break;
                case 7: rank = R.drawable.rank8;break;
                case 8: rank = R.drawable.rank9;break;
                case 9: rank = R.drawable.rank10;break;
                default: rank = R.drawable.rank1;}


            Glide.with(mContext).load(rank).into(rankImg);
            Glide.with(mContext).load(mBurgers.get(position).getImageURL()).into(burgerImg);
            burgerTxt.setText(mBurgers.get(position).getBurgerName());
            restaurantTxt.setText(mBurgers.get(position).getRestaurantName());
            restaurantAddrTxt.setText(mBurgers.get(position).getRestaurantAddress());
            ratingTxt.setText(mBurgers.get(position).getRating());
        }


        //Set on click listeners for views
        burgerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext,"LSNR: Burger "+pos, Toast.LENGTH_SHORT).show();
            }
        });

        return container;
    }
}
