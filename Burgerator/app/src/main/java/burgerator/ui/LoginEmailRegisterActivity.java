package burgerator.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luis.burgerator.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import burgerator.control.Controller;
import burgerator.util.Callback;

public class LoginEmailRegisterActivity extends Activity {

    private LoginEmailRegisterActivity thisActivity = this;
    private EditText mEmail;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mPassword;
    private EditText mZipCode;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email_register);
        Typeface eastwood = Typeface.createFromAsset(getAssets(), "fonts/Eastwood.ttf");

        back = (ImageButton)findViewById(R.id.imgbtn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        //initialize views
        mEmail = (EditText)findViewById(R.id.et_email);
        mEmail.setTypeface(eastwood);
        mFirstName = (EditText)findViewById(R.id.et_first_name);
        mFirstName.setTypeface(eastwood);
        mLastName = (EditText)findViewById(R.id.et_last_name);
        mLastName.setTypeface(eastwood);
        mPassword = (EditText)findViewById(R.id.et_password);
        mPassword.setTypeface(eastwood);
        mZipCode = (EditText)findViewById(R.id.et_zip_code);

        //xip code validation
        final String regex = "\\d{5}(-\\d{4})?";

        mZipCode.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    Log.d("LoginEmailRegisterActivity onCreate()", "User has pressed go!");

                    if(!mFirstName.getText().toString().equals("")) {

                        if(mZipCode.getText().toString().matches(regex)) {
                            //register used based on information given in views
                            //TODO: sanitize and validate the user inputs
                            Controller.
                                    instance().
                                    requestEmailRegister(getApplicationContext(),
                                            mEmail.getText().toString(),
                                            mFirstName.getText().toString(),
                                            mLastName.getText().toString(),
                                            mPassword.getText().toString(),
                                            mZipCode.getText().toString(), new Callback() {
                                                @Override
                                                public void onSuccess(Object result) {
                                                    Log.d("LoginEmailRegisterActivity onGo()", result.toString());

                                                    try {
                                                        JSONObject jsonResult = (JSONObject) result;
                                                        if (jsonResult.getJSONObject("result").getInt("status") == 1) {
                                                            Controller.instance().getUser().setUser((JSONObject)result);

                                                            // Check if no view has focus to hide keyboard going into search activity
                                                            View view = getCurrentFocus();
                                                            if (view != null) {
                                                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                                            }

                                                            //if request was successful then take to burger feed and dont let them come back
                                                            Intent toSearchActivity = new Intent(getApplicationContext(), SearchActivity.class);
                                                            toSearchActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            toSearchActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            startActivity(toSearchActivity);

                                                            thisActivity.finish();
                                                        } else {
                                                            //if request was not successfull, toast and show them result message
                                                            String errorMessage = jsonResult.getJSONObject("result").getJSONObject("error").getString("message");
                                                            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                                                        }
                                                    } catch (JSONException e) {
                                                        Log.e("LoginEmailRegisterActivity error parsing result", e.toString());
                                                    }
                                                }
                                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid zip code", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "enter first name", Toast.LENGTH_LONG).show();
                    }

                    return true;
                }
                return false;
            }
        });

    }

}
