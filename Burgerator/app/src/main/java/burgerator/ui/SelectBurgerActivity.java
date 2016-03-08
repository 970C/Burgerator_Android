package burgerator.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.luis.burgerator.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import burgerator.db.BurgerDB;
import burgerator.util.Callback;
import burgerator.util.SearchAdapter;

/**
 * Created by Luis on 2/15/2016.
 */
public class SelectBurgerActivity extends Activity {

    private ListView mListView;
    private SearchAdapter mAdapter;
    private LayoutInflater mInflater;
    private Button mFooterBtn;
    private Intent mReturn;
    private BurgerDB mBurgerDbRequest;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_select_burger);

        Typeface eastwood = Typeface.createFromAsset(getAssets(), "fonts/Eastwood.ttf");

        //Attach the adapter to the list view
        // Find the ListView
        mListView = (ListView) findViewById(R.id.lv_select_burger);
        ArrayList dummyBurgers = new ArrayList<>();
        mAdapter = new SearchAdapter(this, dummyBurgers );
        mListView.setAdapter(mAdapter);

        //Inflate footerView for footer_view.xml file
        mInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = mInflater.inflate(R.layout.activity_rate_dont_see_your_burger, null, false);
        mFooterBtn = (Button) row.findViewById(R.id.select_burger_footer);

        //Add footerView to ListView
        mListView.addFooterView(mFooterBtn);

        //Add the string to the banner
        TextView bannerBurgerFeed = (TextView)findViewById(R.id.rest_banner);
        bannerBurgerFeed.setText(R.string.title_activity_select_burger);
        bannerBurgerFeed.setGravity(Gravity.CENTER);
        bannerBurgerFeed.setTypeface(eastwood);

        onNewBurger();

        mBurgerDbRequest = new BurgerDB(getApplicationContext());
        mIntent = getIntent();
        JSONObject selectedRestaurant = new JSONObject();
        try {
            selectedRestaurant = new JSONObject(mIntent.getStringExtra("result"));
            mBurgerDbRequest.getBurgersFromRestaurant(
                    selectedRestaurant.getString("id"),
                    new Callback() {
                        @Override
                        public void onSuccess(Object result) {
                            Log.d("SelecBurgerAct.onCreate", ((JSONObject)result).toString());

                        }
                    });
        } catch (JSONException e) {Log.e("SelecBurgerAct.onCreate", e.getMessage());}


        mReturn = new Intent();
        ImageButton back = (ImageButton)findViewById(R.id.imgbtn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mReturn.getStringExtra("result") == null || mReturn.getStringExtra("result").isEmpty())
                    setResult(Activity.RESULT_CANCELED, mReturn);
                else
                    setResult(Activity.RESULT_OK, mReturn);

                finish();
            }
        });
    }

    public void onRestaurantBurgersResponse(){

    }

    //generates the dialog to create a new burger fora restaurant
    public void onNewBurger(){
        final Dialog newBurgerDialog = new Dialog(SelectBurgerActivity.this);
        newBurgerDialog.setContentView(R.layout.activity_select_burger_new_burger);
        newBurgerDialog.setCancelable(true);
        newBurgerDialog.setCanceledOnTouchOutside(false);
        newBurgerDialog.show();
    }
}
