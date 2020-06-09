package com.scoutlabour.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.scoutlabour.R;
import com.scoutlabour.custom.AppConstants;
import com.scoutlabour.custom.PostServiceCall;
import com.scoutlabour.custom.PrefUtils;
import com.scoutlabour.model.AddAddressResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        setToolbar();

        //Getting Intent
        Intent intent = getIntent();


        try {
            JSONObject jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));

            //Displaying payment details
            showDetails(jsonDetails.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Payment Confirmation");
            toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
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

    private void showDetails(JSONObject jsonDetails, String paymentAmount) throws JSONException {
        //Views
//        TextView textViewId = (TextView) findViewById(R.id.paymentId);
//        TextView textViewStatus= (TextView) findViewById(R.id.paymentStatus);
        TextView textViewAmount = (TextView) findViewById(R.id.paymentAmount);

        //Showing the details from json object
//        textViewId.setText(jsonDetails.getString("id"));
//        textViewStatus.setText(jsonDetails.getString("state"));



        textViewAmount.setText("Your Payment of ₹ "+
                paymentAmount+
        " is "+jsonDetails.getString("state")+
        " with Payment ID " + jsonDetails.getString("id"));

            doPostNetworkOperation(jsonDetails.getString("id"),paymentAmount);

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    private void doPostNetworkOperation(String id,String paymentAmount) {


        if (isNetworkConnected()) {

            final ProgressDialog progressDialog = new ProgressDialog(ConfirmationActivity.this);
            progressDialog.setMessage("Submitting.....");
            progressDialog.setCancelable(false);
            progressDialog.show();
            JSONObject registrationObject = new JSONObject();
            try {


                registrationObject.put("category_id", getIntent().getStringExtra("categoryId"));
                registrationObject.put("sub_category_id", getIntent().getStringExtra("subCategoryId"));
                registrationObject.put("address_id",getIntent().getStringExtra("address_id"));
                registrationObject.put("problem_explaination", getIntent().getStringExtra("problem_explaination"));
                registrationObject.put("user_id", PrefUtils.getUser(ConfirmationActivity.this).user_id);
                registrationObject.put("transaction_id", id);
                registrationObject.put("transaction_amount", paymentAmount);

                Log.e("request object", registrationObject+"");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("url main", AppConstants.ADD_NEW_REQUEST);
            new PostServiceCall(AppConstants.ADD_NEW_REQUEST, registrationObject) {

                @Override
                public void response(String response) {

                    Log.e("response", response + "");

                    final AddAddressResponseModel addAddressResponseModel = new GsonBuilder().create().fromJson(response, AddAddressResponseModel.class);

                    if (addAddressResponseModel.status.equalsIgnoreCase("0")) {
                        progressDialog.dismiss();
                        Toast.makeText(ConfirmationActivity.this, addAddressResponseModel.message + "", Toast.LENGTH_LONG).show();


                    } else {

                        JSONObject registrationObject = new JSONObject();
                        try {



                            registrationObject.put("from_user_id", PrefUtils.getUser(ConfirmationActivity.this).user_id);
                            registrationObject.put("to_user_id", "1");
                            registrationObject.put("order_id",addAddressResponseModel.status);
                            registrationObject.put("title", "New Order Request From Customer");
                            registrationObject.put("message", PrefUtils.getUser(ConfirmationActivity.this).name+" sent you request");

                            Log.e("request object", registrationObject+"");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        new PostServiceCall(AppConstants.PUSH_TO_ADMIN, registrationObject) {

                            @Override
                            public void response(String response) {

                                progressDialog.dismiss();
                                Toast.makeText(ConfirmationActivity.this, addAddressResponseModel.message + "", Toast.LENGTH_LONG).show();
//                                finish();
                            }


                            @Override
                            public void error(String error) {
                                progressDialog.dismiss();
                                Toast.makeText(ConfirmationActivity.this, addAddressResponseModel.message + "", Toast.LENGTH_LONG).show();
//                                finish();

                            }
                        }.call();
                    }

                }


                @Override
                public void error(String error) {
                    progressDialog.dismiss();

                    Log.e(" response error...", error + "");
                }
            }.call();


        } else {
            Toast.makeText(ConfirmationActivity.this, "Internet Connection  not Avaiable", Toast.LENGTH_SHORT).show();
        }


    }
}
