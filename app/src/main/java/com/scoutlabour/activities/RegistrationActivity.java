package com.scoutlabour.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.GsonBuilder;
import com.scoutlabour.R;
import com.scoutlabour.app.Config;
import com.scoutlabour.custom.AppConstants;
import com.scoutlabour.custom.PostServiceCall;
import com.scoutlabour.custom.PrefUtils;
import com.scoutlabour.model.RegistrationDetailModel;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity {

    private EditText etFullName,etMobileNumber,etEmailAddress,etPassword,etConfirmPassword;
    private LinearLayout llRegister,llSignIn;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    String regId;

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);

        Log.e("Firebase reg id: ", regId);

//        if (!TextUtils.isEmpty(regId))
//            txtRegId.setText("Firebase Reg Id: " + regId);
//        else
//            txtRegId.setText("Firebase Reg Id is not received yet!");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etFullName= (EditText) findViewById(R.id.etFullName);
        etMobileNumber= (EditText) findViewById(R.id.etMobileNumber);
        etEmailAddress= (EditText) findViewById(R.id.etEmailAddress);
        etPassword= (EditText) findViewById(R.id.etPassword);
        etConfirmPassword= (EditText) findViewById(R.id.etConfirmPassword);

        llRegister= (LinearLayout) findViewById(R.id.llRegister);
        llSignIn= (LinearLayout) findViewById(R.id.llSignIn);
        etFullName= (EditText) findViewById(R.id.etFullName);

        llSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                }
            }
        };



        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);

        llRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etFullName.getText().toString().trim().length()==0)
                {
                    Toast.makeText(RegistrationActivity.this, "Please  Enter Your Full Name", Toast.LENGTH_SHORT).show();
                }else if(etMobileNumber.getText().toString().trim().length()==0){
                    Toast.makeText(RegistrationActivity.this, "Please Enter  Mobile Number", Toast.LENGTH_SHORT).show();
                }else if(!isValidEmail(etEmailAddress.getText().toString().trim())){
                    Toast.makeText(RegistrationActivity.this, "Please Enter Valid Email Address", Toast.LENGTH_SHORT).show();
                }else if(etPassword.getText().toString().trim().length()==0){
                    Toast.makeText(RegistrationActivity.this, "Please Enter  Password", Toast.LENGTH_SHORT).show();
                }else if (!etConfirmPassword.getText().toString().trim().equalsIgnoreCase(etPassword.getText().toString().trim())) {
                    Toast.makeText(RegistrationActivity.this, "Password must be match", Toast.LENGTH_SHORT).show();
                }
                else {

                    doPostNetworkOperation();

                }
            }
        });


setToolbar();

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    private void doPostNetworkOperation() {


        if (isNetworkConnected()) {

            final ProgressDialog progressDialog = new ProgressDialog(RegistrationActivity.this);
            progressDialog.setMessage("Submitting.....");
            progressDialog.setCancelable(false);
            progressDialog.show();

            JSONObject registrationObject = new JSONObject();
            try {


                registrationObject.put("name",  etFullName.getText().toString().trim());
                registrationObject.put("email",  etEmailAddress.getText().toString().trim());
                registrationObject.put("password",  etPassword.getText().toString().trim());
                registrationObject.put("mobile",  etMobileNumber.getText().toString().trim());
                registrationObject.put("notification_id", regId);


            } catch (JSONException e) {
                e.printStackTrace();
            }


            Log.e("url main", AppConstants.REGISTRATION);

            new PostServiceCall(AppConstants.REGISTRATION, registrationObject) {

                @Override
                public void response(String response) {


                    progressDialog.dismiss();
                    Log.e("response", response + "");

                    RegistrationDetailModel registrationDetailModel = new GsonBuilder().create().fromJson(response, RegistrationDetailModel.class);
                    if (registrationDetailModel.message.equalsIgnoreCase("Registration Successfully")) {
                        PrefUtils.setUser(registrationDetailModel, RegistrationActivity.this);
                        Intent i = new Intent(RegistrationActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();


                    } else {
                        Toast.makeText(RegistrationActivity.this, registrationDetailModel.message+"", Toast.LENGTH_SHORT).show();

                    }



                }


                @Override
                public void error(String error) {
                    progressDialog.dismiss();
                    Log.e(" response error...", error + "");
                }
            }.call();



        } else {
            Toast.makeText(RegistrationActivity.this, "Internet Connection  not Avaiable", Toast.LENGTH_SHORT).show();
        }


    }
    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Create Account");
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    private boolean isValidEmail(String email) {

        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }
}
