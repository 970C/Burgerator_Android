package burgerator.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.luis.burgerator.R;


import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import burgerator.control.Controller;
import burgerator.db.BurgerDB;
import burgerator.util.Callback;
import burgerator.util.User;
import burgerator.util.UserStorage;

public class LoginActivity extends AppCompatActivity {

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

    private CallbackManager callbackManager;
    private TextView info;
    private ImageView profileImgView;
    private LoginButton loginButton;
    private AccessToken accessToken;
    //private PrefUtil prefUtil;
    //private IntentUtil intentUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Typeface eastwood = Typeface.createFromAsset(getAssets(), "fonts/Eastwood.ttf");

        //Initializeing Views
        mJoinTheBurgerClub = (ImageView)findViewById(R.id.img_join_the_burger_club);
        mFacebookButton  = (ImageButton) findViewById(R.id.btn_facebook_login);
        //mTwitterButton = (ImageButton) findViewById(R.id.btn_twitter_login);
        mMemberLogin = (ImageView)findViewById(R.id.img_member_login);
        mEmailAddress = (EditText)findViewById(R.id.et_email_address);
        mEmailAddress.setTypeface(eastwood);
        mPassword = (EditText)findViewById(R.id.et_password);
        mPassword.setTypeface(eastwood);
        mForgottenPasswordButton = (Button)findViewById(R.id.link_forgotten_password);
        mForgottenPasswordButton.setTypeface(eastwood);
        mLoginButton = (Button)findViewById(R.id.btn_login);
        mLoginButton.setTypeface(eastwood);

        //Initialize HTTP request handler
        mRequest = new BurgerDB(getApplicationContext());


        //Add the string to the banner
        TextView bannerBurgerFeed = (TextView)findViewById(R.id.et_banner);
        bannerBurgerFeed.setText(R.string.title_activity_burgerator);
        bannerBurgerFeed.setTextSize((float) 30.0);
        bannerBurgerFeed.setGravity(Gravity.CENTER);
        bannerBurgerFeed.setTypeface(eastwood);

        // Initialize the SDK before executing any other operations,
        FacebookSdk.sdkInitialize(getApplicationContext());



        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("FBSuccess", "FB Login success");
                        Log.d("FBloginResult", loginResult.toString());
                        accessToken = loginResult.getAccessToken();
                        if (accessToken != null) {
                            //Toast.makeText(LoginActivity.this, "Your FB token: " + accessToken.getToken(), Toast.LENGTH_LONG).show();
                            Log.d("FBlogin", accessToken.getToken());

                            GraphRequest request = GraphRequest.newMeRequest(
                                    accessToken,
                                    new GraphRequest.GraphJSONObjectCallback() {
                                        @Override
                                        public void onCompleted(
                                                final JSONObject object,
                                                final GraphResponse response) {
                                            // Application code
                                            Log.d("GraphResponse", object.toString());
                                            String email;

                                            try {
                                                email = object.getString("email");
                                                Log.d("Json", email);

                                            }
                                            catch (JSONException e){
                                                Log.d("FB.graphR.callb.JSON e", e.getMessage());
                                                email = "error";
                                            }
                                            UserStorage stor = new UserStorage();
                                            Map<String, String> storVals = new HashMap<String, String>();
                                            storVals.put("email", email);
                                            storVals.put("fbtoken", accessToken.getToken());
                                            stor.setSharedPrefs(getApplicationContext(), "FBUser", storVals);
                                            //stor.setSharedPrefs(getApplicationContext(), "FBUser", "email", email);
                                            //stor.setSharedPrefs(getApplicationContext(), "FBUser", "fbtoken", accessToken.getToken());

                                            //String key = "email";

                                            Log.d("getSharedPref email", stor.getSharedPrefs(getApplicationContext(), "FBUser").toString());
                                            //Log.d("getSharedPref email", stor.getSharedPrefs(getApplicationContext(), "FBUser").get(key).toString());
                                            //mRequest.socialLogin()


                                            Controller.instance().requestSocialLogin(getApplicationContext(), email, accessToken.getToken(), new Callback() {
                                                @Override
                                                public void onSuccess(Object result) {
                                                    JSONObject json = (JSONObject) result;
                                                    final String name;
                                                    final String email;
                                                    final String pw;
                                                    final String img;
                                                    try{
                                                        name = object.getString("name");
                                                        email = object.getString("email");
                                                        pw = json.getJSONObject("result").getJSONObject("content").getString("userpassword");
                                                        img = "https://graph.facebook.com/"+object.get("id")+"/picture?type=normal";
                                                        Log.d("test put name", json.toString());
                                                        Controller.instance().requestUserAuth(email, pw, getApplicationContext(), new Callback() {
                                                            @Override
                                                            public void onSuccess(Object result) {
                                                                Log.d("auth fbuser->email json result", result.toString());
                                                                Controller.instance().setUserName(name);
                                                                Controller.instance().setUserImg(img);

                                                                onLoginResponse((User) result);
                                                            }
                                                        });
                                                    }catch(JSONException e){

                                                    }
                                                }
                                            });
                                        }
                                    });

                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "id,name,email,gender");
                            request.setParameters(parameters);
                            request.executeAsync();
                        }
                    }

                    @Override
                    public void onCancel() {
                        Log.d("FBcancel", "FB Login cancel");
                        Toast.makeText(LoginActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d("FBError", exception.getMessage());
                        Toast.makeText(LoginActivity.this, "FB Login error", Toast.LENGTH_LONG).show();
                    }
                });


    }

    public void forgottenPassword(View view){
        //TODO: take in the user email  and pass it to the server

        if(!mEmailAddress.getText().toString().isEmpty()) {
            mRequest.renewPassword(null, mEmailAddress.getText().toString(), new Callback() {
                @Override
                public void onSuccess(Object result) {
                    JSONObject JSONResult = (JSONObject)result;

                    try {
                        int status = JSONResult.getJSONObject("result").getInt("status");
                        String resultMessage = "error recovering email";
                        if(status == 1){
                            resultMessage = "recovery email sent";
                        }else{
                            resultMessage = JSONResult.getJSONObject("result").getJSONObject("error").getString("message");
                        }
                        Toast.makeText(getApplicationContext(), resultMessage, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        Log.e("LoginAct.forgottPass", e.getMessage());
                    }
                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "please enter an email", Toast.LENGTH_LONG).show();
        }
    }

    public void emailLogin(View view){
        // takes in email address and password and checks with server

        String e = mEmailAddress.getText().toString();
        String p = mPassword.getText().toString();

        if(!e.isEmpty()) {
            Controller.instance().requestUserAuth(e, p, getApplicationContext(), new Callback() {
                @Override
                public void onSuccess(Object result) {

                    try {
                        JSONObject JSONResult = new JSONObject(result.toString());
                        int status = JSONResult.getJSONObject("result").getInt("status");
                        String resultMessage = "error logging in";
                        if (status == 1) {
                            System.out.print(Log.DEBUG);
                            onLoginResponse((User) result);
                        } else {
                            resultMessage = JSONResult.getJSONObject("result").getJSONObject("error").getString("message");
                            Toast.makeText(getApplicationContext(), resultMessage, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Log.e("LoginAct.forgottPass", e.getMessage());
                    }
                }
            });
        }else{Toast.makeText(getApplicationContext(), "email is empty", Toast.LENGTH_LONG).show();}
    }

    //public void onLoginResponse(JSONObject response){
    public void onLoginResponse(User response){
        Log.d("LoginActivity","onLoginResponse(): " + response.toString());

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

        //callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.d("FBlogin", "FB Login ");
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
        //Log.d("FBlogin", AccessToken.getCurrentAccessToken().toString());

    }

    public void twitterRegister(View view){
        //TODO: sends to a new activity that lists twitter accouts on the device
    }

    public void emailRegister(View view){
        //sends to a new activity that allows the user to register
        Intent registerByEmail = new Intent(this,LoginEmailRegisterActivity.class);
        startActivity(registerByEmail);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    //code from example - may remove in future
    private String message(Profile profile) {
        StringBuilder stringBuffer = new StringBuilder();
        if (profile != null) {
            stringBuffer.append("Welcome ").append(profile.getName());
        }
        return stringBuffer.toString();
    }

    //code from example - may remove in future
    private void deleteAccessToken() {
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null){
                    //User logged out
                    //prefUtil.clearToken();
                    //clearUserArea();
                }
            }
        };
    }
}
