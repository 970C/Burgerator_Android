package burgerator.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luis.burgerator.R;

import org.json.JSONObject;

import burgerator.control.Controller;
import burgerator.db.BurgerDB;
import burgerator.util.Callback;
import burgerator.util.User;

public class LoginActivity extends Activity {

    private ImageView mJoinTheBurgerClub;
    private ImageButton mFacebookButton;
    private ImageButton mTwitterButton;
    private ImageView mMemberLogin;
    private EditText mEmailAddress;
    private EditText mPassword;
    private Button mForgottenPasswordButton;
    private Button mLoginButton;

    // HTTP request handler
    private BurgerDB mRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initializeing Views
        mJoinTheBurgerClub = (ImageView)findViewById(R.id.img_join_the_burger_club);
        mFacebookButton  = (ImageButton) findViewById(R.id.btn_facebook_login);
        mTwitterButton = (ImageButton) findViewById(R.id.btn_twitter_login);
        mMemberLogin = (ImageView)findViewById(R.id.img_member_login);
        mEmailAddress = (EditText)findViewById(R.id.et_email_address);
        mPassword = (EditText)findViewById(R.id.et_password);
        mForgottenPasswordButton = (Button)findViewById(R.id.link_forgotten_password);
        mLoginButton = (Button)findViewById(R.id.btn_login);

        //Initialize HTTP request handler
        mRequest = new BurgerDB(getApplicationContext());

        //Add the string to the banner
        TextView bannerBurgerFeed = (TextView)findViewById(R.id.et_banner);
        bannerBurgerFeed.setText(R.string.title_activity_burgerator);
        bannerBurgerFeed.setTextSize((float)30.0);
        bannerBurgerFeed.setGravity(Gravity.CENTER);

    }

    public void forgottenPassword(View view){
        //TODO: take in the user email  and pass it to the server
    }

    public void emailLogin(View view){
        // takes in email address and password and checks with server

        /*
        mRequest.emailLogin(null, mEmailAddress.getText().toString(),
                mPassword.getText().toString(),
                new BurgerDB.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        onLoginResponse(response);
                    }
                });
           */

        String e = mEmailAddress.getText().toString();
        String p = mPassword.getText().toString();

        Controller.instance().requestUserAuth(e, p, getApplicationContext(), new Callback() {
            @Override
            public void onSuccess(Object result) {
                System.out.print(Log.DEBUG);
                onLoginResponse((User) result);
            }
        });
    }

    //public void onLoginResponse(JSONObject response){
    public void onLoginResponse(User response){
        Log.d("Burgerator LoginActivity","onLoginResponse(): " + response.toString());

        //TODO: if status 1 go to search, if status 0 open error dialog
        //UserOLD.instance().setUser(response); old

        Log.d("Burgerator UserOLD Test", response.toString());

        //Log.d("Burgerator UserOLD Test getEmail", response.getEmail());
        //Log.d("Burgerator UserOLD Test getUserPassword", response.getUserPassword());
        //Log.d("Burgerator UserOLD Test getResult", response.getResult());
        //Log.d("Burgerator Sanity test", "Sanity Test");

        //Go to search activity
        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
        startActivity(intent);
    }

    public void facebookRegister(View view){
        //TODO: opens a dialog to validate facebook
    }

    public void twitterRegister(View view){
        //TODO: sends to a new activity that lists twitter accouts on the device
    }

    public void emailRegister(View view){
        //TODO: sends to a new activity that allows the user to register
    }
}
