package com.example.luis.burgerator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import org.json.JSONObject;

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

        //TODO: set banners text

    }

    public void forgottenPassword(View view){
        //TODO: take in the user email  and pass it to the server
    }

    public void emailLogin(View view){
        // takes in email address and password and checks with server
        mRequest.emailLogin(null, mEmailAddress.getText().toString(),
                            mPassword.getText().toString(),
                            new BurgerDB.VolleyCallback() {
                                @Override
                                public void onSuccess(JSONObject result) {
                                    //TODO: if 1 go to search, if 0 open error dialog
                                    mPassword.setText("pass");
                                }
                            });

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
