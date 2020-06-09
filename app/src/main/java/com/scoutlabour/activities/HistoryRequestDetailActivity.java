package com.scoutlabour.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.GsonBuilder;
import com.scoutlabour.R;
import com.scoutlabour.custom.AppConstants;
import com.scoutlabour.custom.GetServiceCall;
import com.scoutlabour.custom.PostServiceCall;
import com.scoutlabour.custom.PrefUtils;
import com.scoutlabour.custom.RVEmptyObserver;
import com.scoutlabour.model.NewRequestDetailModel;
import com.scoutlabour.model.RegistrationDetailModel;
import com.scoutlabour.model.SubCategoryDetailListModel;
import com.scoutlabour.model.labour.StatusModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class HistoryRequestDetailActivity extends AppCompatActivity {

    private TextView txtPaymentDetails,txtTime,txtDate,txtDateRequestSent,txtDateWorkAssigned,txtWorkerName,txtWorkerMobileNumber,txtCustomerName,txtCutomerMobileNumber,txtAddress,txtProblemDetail;
    private LinearLayout llAddWorker,llSelectDate,llSelectTime,llAssignWorker;
    private ImageView imgIdProof,imgWorker,imgWorkerMobile,imgCustomerMobile;
    private ArrayList<NewRequestDetailModel> newRequestDetailModels;
    private   TextView txtAddressName, txtHouseNo, txtLandMark, txtCityPincode, txtState;
    private boolean isTablet;
    private int selectedItemPosition;

    EditText etFeedback;
    TextView tvThanks;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_request_detail_user);
        isTablet = getResources().getBoolean(R.bool.isTablet);
        selectedItemPosition = getIntent().getIntExtra("position", 0);
        try {
            newRequestDetailModels = PrefUtils.getRequestListDetail(HistoryRequestDetailActivity.this).newRequestDetailModelArrayList;
        } catch (Exception e) {
            e.printStackTrace();
            newRequestDetailModels = new ArrayList<NewRequestDetailModel>();

        }
        if(getIntent().getBooleanExtra("from_notification",false)){
            selectedItemPosition=0;
        }

        Log.e("screen width", isTablet + "");
        final Calendar cal = Calendar.getInstance();
        txtDate= (TextView) findViewById(R.id.txtDate);
        txtTime= (TextView) findViewById(R.id.txtTime);
        txtDateRequestSent= (TextView) findViewById(R.id.txtDateRequestSent);
        txtDateWorkAssigned= (TextView) findViewById(R.id.txtDateWorkAssigned);
        txtWorkerName= (TextView) findViewById(R.id.txtWorkerName);
        txtWorkerMobileNumber= (TextView) findViewById(R.id.txtWorkerMobileNumber);
        txtCustomerName= (TextView) findViewById(R.id.txtCustomerName);
        txtCutomerMobileNumber= (TextView) findViewById(R.id.txtCutomerMobileNumber);
        txtAddress= (TextView) findViewById(R.id.txtAddress);
        txtProblemDetail= (TextView) findViewById(R.id.txtProblemDetail);
        txtPaymentDetails= (TextView) findViewById(R.id.txtPaymentDetails);

        etFeedback= (EditText) findViewById(R.id.etFeedback);
        tvThanks= (TextView) findViewById(R.id.tvThanks);
        btnSubmit= (Button) findViewById(R.id.btnSubmit);

////        llAddWorker= (LinearLayout) findViewById(R.id.llAddWorker);
//        llSelectDate= (LinearLayout) findViewById(R.id.llSelectDate);
//        llSelectTime= (LinearLayout) findViewById(R.id.llSelectTime);
////        llAssignWorker= (LinearLayout) findViewById(R.id.llAssignWorker);

        imgIdProof= (ImageView) findViewById(R.id.imgIdProof);
        imgWorker= (ImageView) findViewById(R.id.imgWorker);
        imgWorkerMobile= (ImageView) findViewById(R.id.imgWorkerMobile);
        imgCustomerMobile= (ImageView) findViewById(R.id.imgCustomerMobile);

        txtAddressName = (TextView) findViewById(R.id.txtAddressName);
        txtHouseNo = (TextView) findViewById(R.id.txtHouseNo);
        txtLandMark = (TextView) findViewById(R.id.txtLandMark);
        txtCityPincode = (TextView) findViewById(R.id.txtCityPincode);
        txtState = (TextView) findViewById(R.id.txtState);
        imgCustomerMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String number = txtCutomerMobileNumber.getText().toString();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));
                startActivity(callIntent);


            }
        });

        imgWorkerMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String number = txtWorkerMobileNumber.getText().toString();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));
                startActivity(callIntent);
            }
        });

        try {


            txtDate.setText(newRequestDetailModels.get(selectedItemPosition).service_date);
            txtTime.setText(newRequestDetailModels.get(selectedItemPosition).service_time);

            txtDateRequestSent.setText(newRequestDetailModels.get(selectedItemPosition).created_at);
            txtDateWorkAssigned.setText(newRequestDetailModels.get(selectedItemPosition).assigned_at);
            if (isTablet) {


                Glide.with(HistoryRequestDetailActivity.this)
                        .load(newRequestDetailModels.get(selectedItemPosition).worker_id_image_large).thumbnail(0.05f).crossFade().fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(imgIdProof);
            } else {
                Glide.with(HistoryRequestDetailActivity.this)
                        .load(newRequestDetailModels.get(selectedItemPosition).worker_id_image_med).thumbnail(0.05f).crossFade().fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(imgIdProof);


            }
            if (isTablet) {


                Glide.with(HistoryRequestDetailActivity.this)
                        .load(newRequestDetailModels.get(selectedItemPosition).worker_profile_image_large).thumbnail(0.05f).crossFade().fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(imgWorker);
            } else {
                Glide.with(HistoryRequestDetailActivity.this)
                        .load(newRequestDetailModels.get(selectedItemPosition).worker_profile_image_med).thumbnail(0.05f).crossFade().fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(imgWorker);


            }

            txtWorkerMobileNumber.setText(newRequestDetailModels.get(selectedItemPosition).worker_mobile);
            txtWorkerName.setText(newRequestDetailModels.get(selectedItemPosition).worker_name);
            txtProblemDetail.setText(newRequestDetailModels.get(selectedItemPosition).problem_explaination);
            txtCustomerName.setText(newRequestDetailModels.get(selectedItemPosition).user_name);
            txtCutomerMobileNumber.setText(newRequestDetailModels.get(selectedItemPosition).user_mobile);
            txtAddressName.setText(newRequestDetailModels.get(selectedItemPosition).address_name);
            txtHouseNo.setText(newRequestDetailModels.get(selectedItemPosition).address_1+", "+newRequestDetailModels.get(selectedItemPosition).address_2);
            txtLandMark.setText(newRequestDetailModels.get(selectedItemPosition).address_3);
            txtCityPincode.setText(newRequestDetailModels.get(selectedItemPosition).city + ", " + newRequestDetailModels.get(selectedItemPosition).pincode);
            txtState.setText(newRequestDetailModels.get(selectedItemPosition).state);

            txtPaymentDetails.setText("Transaction ID:- "+newRequestDetailModels.get(selectedItemPosition).transaction_id+"\n"+
                    "Transaction Amount:- ₹ "+ newRequestDetailModels.get(selectedItemPosition).transaction_amount);

        } catch (Exception e) {
            e.printStackTrace();
        }
        setToolbar();


        if(newRequestDetailModels.get(selectedItemPosition).feedback_given.equalsIgnoreCase("0")){
            etFeedback.setVisibility(View.VISIBLE);
            tvThanks.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.VISIBLE);
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doPostNetworkOperation();
                }
            });
        }else{
            etFeedback.setVisibility(View.GONE);
            tvThanks.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.GONE);
        }


    }
    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("History Request Detail");
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

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    private void doPostNetworkOperation() {


        if (isNetworkConnected()) {

            final ProgressDialog progressDialog = new ProgressDialog(HistoryRequestDetailActivity.this);
            progressDialog.setMessage("submitting.....");
            progressDialog.setCancelable(false);
            progressDialog.show();

            JSONObject requestObject = new JSONObject();

            try {
                requestObject.put("order_id", newRequestDetailModels.get(selectedItemPosition).order_id);
                requestObject.put("feedback", etFeedback.getText().toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            new PostServiceCall(AppConstants.FEEDBACK, requestObject) {
                @Override
                public void response(String response) {
                    Log.e("response", response);
                    progressDialog.dismiss();

                    StatusModel registrationDetailModel = new GsonBuilder().create().fromJson(response, StatusModel.class);

                    if (registrationDetailModel.status.equalsIgnoreCase("1")) {
                        Toast.makeText(HistoryRequestDetailActivity.this, registrationDetailModel.msg+"", Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                        Toast.makeText(HistoryRequestDetailActivity.this, registrationDetailModel.msg+"", Toast.LENGTH_SHORT).show();

                    }



                }

                @Override
                public void error(String error) {
                    Log.e("response", error.toString());
                    progressDialog.dismiss();
                }


            }.call();

        } else {
            Toast.makeText(HistoryRequestDetailActivity.this, "No internet connection available...", Toast.LENGTH_SHORT).show();
        }
    }

}
