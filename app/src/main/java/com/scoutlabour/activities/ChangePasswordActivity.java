package com.scoutlabour.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.scoutlabour.R;
import com.scoutlabour.custom.AppConstants;
import com.scoutlabour.custom.PostServiceCall;
import com.scoutlabour.custom.PrefUtils;
import com.scoutlabour.model.AddAddressResponseModel;
import com.scoutlabour.model.ChangePasswordResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText etOldPassword,etNewPassword,etConfirmNewPassword;
    private LinearLayout llConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        etOldPassword= (EditText) findViewById(R.id.etOldPassword);
        etNewPassword= (EditText) findViewById(R.id.etNewPassword);
        etConfirmNewPassword= (EditText) findViewById(R.id.etConfirmNewPassword);

        llConfirmPassword= (LinearLayout) findViewById(R.id.llConfirmPassword);

        llConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etOldPassword.getText().toString().trim().length()==0)
                {
                    Toast.makeText(ChangePasswordActivity.this, "Please  Enter Old Password", Toast.LENGTH_SHORT).show();
                }else if(etNewPassword.getText().toString().trim().length()==0){
                    Toast.makeText(ChangePasswordActivity.this, "Please Enter  New Password", Toast.LENGTH_SHORT).show();
                }else if (!etConfirmNewPassword.getText().toString().trim().equalsIgnoreCase(etNewPassword.getText().toString().trim())) {
                    Toast.makeText(ChangePasswordActivity.this, "Password must be match", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(isNetworkConnected()){
                        String newPassword, confirmPassword;
                        newPassword = etNewPassword.getText().toString().trim();
                        confirmPassword = etConfirmNewPassword.getText().toString().trim();
                        if (newPassword.equals(confirmPassword)) {
                            final ProgressDialog     progressDialog = new ProgressDialog(ChangePasswordActivity.this);
                            progressDialog.setMessage("Submitting...");
                            progressDialog.show();

                            final JSONObject registrationObject = new JSONObject();


                            try {
                                registrationObject.put("user_id", PrefUtils.getUser(ChangePasswordActivity.this).user_id);
                                registrationObject.put("password", etOldPassword.getText().toString().trim());
                                registrationObject.put("new_password", etNewPassword.getText().toString().trim());


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Log.e("registration object", registrationObject + "");

                            new PostServiceCall(AppConstants.CHANGE_PASSWORD, registrationObject) {

                                @Override
                                public void response(String response) {
                                    progressDialog.dismiss();
                                    ChangePasswordResponseModel changePasswordResponseModel = new GsonBuilder().create().fromJson(response, ChangePasswordResponseModel.class);

                                    if (changePasswordResponseModel.status.equalsIgnoreCase("1")) {
                                        Toast.makeText(ChangePasswordActivity.this, changePasswordResponseModel.message + "", Toast.LENGTH_LONG).show();

                                        finish();
                                    } else {
                                        Toast.makeText(ChangePasswordActivity.this, changePasswordResponseModel.message + "", Toast.LENGTH_LONG).show();
                                    }


                                }

                                @Override
                                public void error(String error) {
                                    progressDialog.dismiss();
                                    Log.e("json response...", error + "");

                                }


                            }.call();



                        } else {
                            Toast.makeText(ChangePasswordActivity.this, "Password Dose Not Match", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(ChangePasswordActivity.this, "Internet Connection  not Avaiable", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

        setToolbar();
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Change Password");
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_close);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

}
