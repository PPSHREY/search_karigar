package com.scoutlabour.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.scoutlabour.R;
import com.scoutlabour.custom.PrefUtils;
import com.scoutlabour.model.NewRequestDetailModel;

import java.util.ArrayList;
import java.util.Calendar;

public class InProgressRequestActivity extends AppCompatActivity {

    private TextView txtTime,txtDate,txtDateRequestSent,txtDateWorkAssigned,txtWorkerName,txtWorkerMobileNumber,txtCustomerName,txtCutomerMobileNumber,txtAddress,txtProblemDetail;
    private LinearLayout llAddWorker,llSelectDate,llSelectTime,llAssignWorker;
    private ImageView imgIdProof,imgWorker,imgWorkerMobile,imgCustomerMobile;
    private ArrayList<NewRequestDetailModel> newRequestDetailModels;
    private   TextView txtAddressName, txtHouseNo, txtLandMark, txtCityPincode, txtState;
    private boolean isTablet;
    private int selectedItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_progress_request);
        isTablet = getResources().getBoolean(R.bool.isTablet);
        selectedItemPosition = getIntent().getIntExtra("position", 0);
        try {
            newRequestDetailModels = PrefUtils.getRequestListDetail(InProgressRequestActivity.this).newRequestDetailModelArrayList;
        } catch (Exception e) {
            e.printStackTrace();
            newRequestDetailModels = new ArrayList<NewRequestDetailModel>();

        }


        Log.e("screen width", isTablet + "");
        final Calendar cal = Calendar.getInstance();
//        txtDate= (TextView) findViewById(R.id.txtDate);
//        txtTime= (TextView) findViewById(R.id.txtTime);
        txtDateRequestSent= (TextView) findViewById(R.id.txtDateRequestSent);
        txtDateWorkAssigned= (TextView) findViewById(R.id.txtDateWorkAssigned);

//        txtWorkerName= (TextView) findViewById(R.id.txtWorkerName);
//        txtWorkerMobileNumber= (TextView) findViewById(R.id.txtWorkerMobileNumber);
//        txtCustomerName= (TextView) findViewById(R.id.txtCustomerName);
//        txtCutomerMobileNumber= (TextView) findViewById(R.id.txtCutomerMobileNumber);
//        txtAddress= (TextView) findViewById(R.id.txtAddress);
//        txtProblemDetail= (TextView) findViewById(R.id.txtProblemDetail);

////        llAddWorker= (LinearLayout) findViewById(R.id.llAddWorker);
//        llSelectDate= (LinearLayout) findViewById(R.id.llSelectDate);
//        llSelectTime= (LinearLayout) findViewById(R.id.llSelectTime);
////        llAssignWorker= (LinearLayout) findViewById(R.id.llAssignWorker);

//        imgIdProof= (ImageView) findViewById(R.id.imgIdProof);
//        imgWorker= (ImageView) findViewById(R.id.imgWorker);
//        imgWorkerMobile= (ImageView) findViewById(R.id.imgWorkerMobile);
//        imgCustomerMobile= (ImageView) findViewById(R.id.imgCustomerMobile);
//
//        txtAddressName = (TextView) findViewById(R.id.txtAddressName);
//        txtHouseNo = (TextView) findViewById(R.id.txtHouseNo);
//        txtLandMark = (TextView) findViewById(R.id.txtLandMark);
//        txtCityPincode = (TextView) findViewById(R.id.txtCityPincode);
//        txtState = (TextView) findViewById(R.id.txtState);
//        imgCustomerMobile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String number = txtCutomerMobileNumber.getText().toString();
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setData(Uri.parse("tel:" + number));
//                startActivity(callIntent);
//
//
//            }
//        });
//
//        imgWorkerMobile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String number = txtWorkerMobileNumber.getText().toString();
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setData(Uri.parse("tel:" + number));
//                startActivity(callIntent);
//            }
//        });



        txtDateRequestSent.setText(newRequestDetailModels.get(selectedItemPosition).created_at);


//        try {
//
//
//
//            txtDateRequestSent.setText(newRequestDetailModels.get(selectedItemPosition).created_at);
////            txtDateWorkAssigned.setText(newRequestDetailModels.get(selectedItemPosition).assigned_at);
//            if (isTablet) {
//
//
//                Glide.with(InProgressRequestActivity.this)
//                        .load(newRequestDetailModels.get(selectedItemPosition).worker_id_image_large).thumbnail(0.05f).crossFade().fitCenter()
//                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(imgIdProof);
//            } else {
//                Glide.with(InProgressRequestActivity.this)
//                        .load(newRequestDetailModels.get(selectedItemPosition).worker_id_image_med).thumbnail(0.05f).crossFade().fitCenter()
//                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(imgIdProof);
//
//
//            }
//            if (isTablet) {
//
//
//                Glide.with(InProgressRequestActivity.this)
//                        .load(newRequestDetailModels.get(selectedItemPosition).worker_profile_image_large).thumbnail(0.05f).crossFade().fitCenter()
//                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(imgWorker);
//            } else {
//                Glide.with(InProgressRequestActivity.this)
//                        .load(newRequestDetailModels.get(selectedItemPosition).worker_profile_image_med).thumbnail(0.05f).crossFade().fitCenter()
//                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(imgWorker);
//
//
//            }
//
//            txtWorkerMobileNumber.setText(newRequestDetailModels.get(selectedItemPosition).worker_mobile);
//            txtWorkerName.setText(newRequestDetailModels.get(selectedItemPosition).worker_name);
//            txtProblemDetail.setText(newRequestDetailModels.get(selectedItemPosition).problem_explaination);
//            txtCustomerName.setText(newRequestDetailModels.get(selectedItemPosition).user_name);
//            txtCutomerMobileNumber.setText(newRequestDetailModels.get(selectedItemPosition).user_mobile);
//            txtAddressName.setText(newRequestDetailModels.get(selectedItemPosition).address_name);
//            txtHouseNo.setText(newRequestDetailModels.get(selectedItemPosition).address_1+", "+newRequestDetailModels.get(selectedItemPosition).address_2);
//            txtLandMark.setText(newRequestDetailModels.get(selectedItemPosition).address_3);
//            txtCityPincode.setText(newRequestDetailModels.get(selectedItemPosition).city + ", " + newRequestDetailModels.get(selectedItemPosition).pincode);
//            txtState.setText(newRequestDetailModels.get(selectedItemPosition).state);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        setToolbar();
    }
    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Request Detail");
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
}
