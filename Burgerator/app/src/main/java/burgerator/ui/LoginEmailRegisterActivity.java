package burgerator.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luis.burgerator.R;

import burgerator.control.Controller;
import burgerator.util.Callback;

public class LoginEmailRegisterActivity extends Activity {

    private EditText mEmail;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mPassword;
    private EditText mZipCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email_register);

        //initialize views
        mEmail = (EditText)findViewById(R.id.et_email);
        mFirstName = (EditText)findViewById(R.id.et_first_name);
        mLastName = (EditText)findViewById(R.id.et_last_name);
        mPassword = (EditText)findViewById(R.id.et_password);
        mZipCode = (EditText)findViewById(R.id.et_zip_code);

        mZipCode.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    Log.d("LoginEmailRegisterActivity onCreate()", "User has pressed go!");

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
                                            //if request was successful then take to burger feed
                                            Intent toSearchActivity = new Intent(getApplicationContext(),SearchActivity.class);
                                            startActivity(toSearchActivity);
                                        }
                                    });

                    return true;
                }
                return false;
            }
        });

    }


}
