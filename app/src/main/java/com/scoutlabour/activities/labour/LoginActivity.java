package com.scoutlabour.activities.labour;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.scoutlabour.R;
import com.scoutlabour.custom.AppConstants;
import com.scoutlabour.custom.PostServiceCall;
import com.scoutlabour.custom.PrefUtils;
import com.scoutlabour.model.labour.RegistrationLabourDetailModel;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    private EditText etEmailAddress,etPassword;
    private LinearLayout llLogin;
    private TextView txtForgotPassword,txtSignUp;
    private RegistrationLabourDetailModel user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //check if user already login or not

        try {
            user = PrefUtils.getlabour(LoginActivity.this);
            if (user.worker_id.length() > 0) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
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

            } catch (JSONException e) {
                e.printStackTrace();
            }


            new PostServiceCall(AppConstants.KARIGAR_LOGIN, requestObject) {
                @Override
                public void response(String response) {
                    Log.e("response", response);
                    progressDialog.dismiss();

                    RegistrationLabourDetailModel registrationDetailModel = new GsonBuilder().create().fromJson(response, RegistrationLabourDetailModel.class);

                    if (registrationDetailModel.message.equalsIgnoreCase("Login Successfully")) {
                        PrefUtils.setlabour(registrationDetailModel, LoginActivity.this);
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
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
