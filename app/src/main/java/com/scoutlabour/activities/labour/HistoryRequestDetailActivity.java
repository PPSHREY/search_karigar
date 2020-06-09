package com.scoutlabour.activities.labour;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scoutlabour.R;
import com.scoutlabour.custom.PrefUtils;
import com.scoutlabour.model.NewRequestDetailModel;

import java.util.ArrayList;
import java.util.Calendar;

public class HistoryRequestDetailActivity extends AppCompatActivity {

    private TextView txtPaymentDetails,txtTime,txtDate,txtDateRequestSent,txtDateWorkAssigned,txtWorkerName,txtWorkerMobileNumber,txtCustomerName,txtCutomerMobileNumber,txtAddress,txtProblemDetail;
    private LinearLayout llAddWorker,llSelectDate,llSelectTime,llAssignWorker,llPayment;
    private ImageView imgIdProof,imgWorker,imgWorkerMobile,imgCustomerMobile,imgGetLocation;
    private ArrayList<NewRequestDetailModel> newRequestDetailModels;
    private   TextView txtAddressName, txtHouseNo, txtLandMark, txtCityPincode, txtState;
    private boolean isTablet;
    private int selectedItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_request_detail);
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
//        txtAddress= (TextView) findViewById(R.id.txtAddress);
        txtProblemDetail= (TextView) findViewById(R.id.txtProblemDetail);
        txtPaymentDetails= (TextView) findViewById(R.id.txtPaymentDetails);

        llPayment= (LinearLayout) findViewById(R.id.llPayment);
//        llSelectDate= (LinearLayout) findViewById(R.id.llSelectDate);
//        llSelectTime= (LinearLayout) findViewById(R.id.llSelectTime);
////        llAssignWorker= (LinearLayout) findViewById(R.id.llAssignWorker);

        imgIdProof= (ImageView) findViewById(R.id.imgIdProof);
        imgWorker= (ImageView) findViewById(R.id.imgWorker);
        imgWorkerMobile= (ImageView) findViewById(R.id.imgWorkerMobile);
        imgCustomerMobile= (ImageView) findViewById(R.id.imgCustomerMobile);
        imgGetLocation= (ImageView) findViewById(R.id.imgGetLocation);

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


        imgGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lat_long = "geo:0,0"+"?q="+txtHouseNo.getText().toString()+txtLandMark.getText().toString()
                        +txtCityPincode.getText().toString()+txtState.getText().toString();
                Log.e("lat_long",lat_long);
                Uri gmmIntentUri = Uri.parse(lat_long);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });


        try {
            txtDate.setText(newRequestDetailModels.get(selectedItemPosition).service_date);
            txtTime.setText(newRequestDetailModels.get(selectedItemPosition).service_time);

            txtDateRequestSent.setText(newRequestDetailModels.get(selectedItemPosition).created_at);
            txtDateWorkAssigned.setText(newRequestDetailModels.get(selectedItemPosition).assigned_at);
//

//            txtWorkerMobileNumber.setText(newRequestDetailModels.get(selectedItemPosition).user_mobile);
//            txtWorkerName.setText(newRequestDetailModels.get(selectedItemPosition).user_name);
            txtProblemDetail.setText(newRequestDetailModels.get(selectedItemPosition).problem_explaination);
            txtCustomerName.setText(newRequestDetailModels.get(selectedItemPosition).user_name);
            txtCutomerMobileNumber.setText(newRequestDetailModels.get(selectedItemPosition).user_mobile);
            txtAddressName.setText(newRequestDetailModels.get(selectedItemPosition).address_name);
            txtHouseNo.setText(newRequestDetailModels.get(selectedItemPosition).address_1+", "+newRequestDetailModels.get(selectedItemPosition).address_2);
            txtLandMark.setText(newRequestDetailModels.get(selectedItemPosition).address_3);
            txtCityPincode.setText(newRequestDetailModels.get(selectedItemPosition).city + ", " + newRequestDetailModels.get(selectedItemPosition).pincode);
            txtState.setText(newRequestDetailModels.get(selectedItemPosition).state);

            if(getIntent().getStringExtra("type").equalsIgnoreCase("inprogress")){
                llPayment.setVisibility(View.GONE);
            }else{
                llPayment.setVisibility(View.VISIBLE);
                txtPaymentDetails.setText("Transaction Amount:- ₹ "+ newRequestDetailModels.get(selectedItemPosition).transaction_amount);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        setToolbar();
    }
    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            if(getIntent().getStringExtra("type").equalsIgnoreCase("inprogress")){
                toolbar.setTitle("InProgress Request Detail");
            }else{
                toolbar.setTitle("History Request Detail");
            }
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
