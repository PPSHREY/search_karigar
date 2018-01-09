package com.scoutlabour.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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


public class LoginActivity extends AppCompatActivity {

    private EditText etEmailAddress,etPassword;
    private LinearLayout llLogin;
    private TextView txtForgotPassword,txtSignUp;
    private RegistrationDetailModel user;
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
        setContentView(R.layout.activity_login);
        //check if user already login or not
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

        try {
            user = PrefUtils.getUser(LoginActivity.this);
            if (user.user_id.length() > 0) {
                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        etEmailAddress= (EditText) findViewById(R.id.etEmailAddress);
        etPassword= (EditText) findViewById(R.id.etPassword);
        llLogin= (LinearLayout) findViewById(R.id.llLogin);

        txtForgotPassword= (TextView) findViewById(R.id.txtForgotPassword);
        txtSignUp= (TextView) findViewById(R.id.txtSignUp);

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(i);
            }
        });

        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(i);
            }
        });
        llLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etEmailAddress.getText().toString().trim().length()==0) {
                    Toast.makeText(LoginActivity.this, "Please Enter  Email Address", Toast.LENGTH_SHORT).show();
                }else if(etPassword.getText().toString().trim().length()==0){
                    Toast.makeText(LoginActivity.this, "Please Enter  Password", Toast.LENGTH_SHORT).show();
                }else {
                    doPostNetworkOperation();

                }


            }
        });


    }
    private boolean isValidEmail(String email) {

        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    private void doPostNetworkOperation() {


        if (isNetworkConnected()) {

            final ProgressDialog      progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("submitting.....");
            progressDialog.setCancelable(false);
            progressDialog.show();

            JSONObject requestObject = new JSONObject();

            try {


                requestObject.put("email", etEmailAddress.getText().toString().trim());
                requestObject.put("password", etPassword.getText().toString().trim());
                requestObject.put("notification_id", regId);

            } catch (JSONException e) {
                e.printStackTrace();
            }





            new PostServiceCall(AppConstants.LOGIN, requestObject) {
                @Override
                public void response(String response) {
                    Log.e("response", response);
                    progressDialog.dismiss();

                    RegistrationDetailModel registrationDetailModel = new GsonBuilder().create().fromJson(response, RegistrationDetailModel.class);

                    if (registrationDetailModel.message.equalsIgnoreCase("Login Successfully")) {
                        PrefUtils.setUser(registrationDetailModel, LoginActivity.this);
                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();



                    } else {
                        Toast.makeText(LoginActivity.this, registrationDetailModel.message+"", Toast.LENGTH_SHORT).show();

                    }



                }

                @Override
                public void error(String error) {
                    Log.e("response", error.toString());
                    progressDialog.dismiss();
                }


            }.call();

        } else {
            Toast.makeText(LoginActivity.this, "No internet connection available...", Toast.LENGTH_SHORT).show();
        }
    }


}
