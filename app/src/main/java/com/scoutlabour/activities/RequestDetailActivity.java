package com.scoutlabour.activities;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scoutlabour.R;

import java.util.Calendar;

public class RequestDetailActivity extends AppCompatActivity {

    private TextView txtDateRequestSent,txtDateWorkAssigned,txtWorkerName,txtMobileNumber,txtCustomerName,txtCutomerMobileNumber,txtAddress,txtProblemDetail;
    private LinearLayout llAddWorker,llSelectDate,llSelectTime,llAssignWorker;
    private ImageView imgIdProof,imgWorker,imgWorkerMobile,imgCustomerMobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail);

        txtDateRequestSent= (TextView) findViewById(R.id.txtDateRequestSent);
        txtDateWorkAssigned= (TextView) findViewById(R.id.txtDateWorkAssigned);
        txtWorkerName= (TextView) findViewById(R.id.txtWorkerName);
        txtMobileNumber= (TextView) findViewById(R.id.txtMobileNumber);
        txtCustomerName= (TextView) findViewById(R.id.txtCustomerName);
        txtCutomerMobileNumber= (TextView) findViewById(R.id.txtCutomerMobileNumber);
        txtAddress= (TextView) findViewById(R.id.txtAddress);
        txtProblemDetail= (TextView) findViewById(R.id.txtProblemDetail);

        llAddWorker= (LinearLayout) findViewById(R.id.llAddWorker);
        llSelectDate= (LinearLayout) findViewById(R.id.llSelectDate);
        llSelectTime= (LinearLayout) findViewById(R.id.llSelectTime);
        llAssignWorker= (LinearLayout) findViewById(R.id.llAssignWorker);

        imgIdProof= (ImageView) findViewById(R.id.imgIdProof);
        imgWorker= (ImageView) findViewById(R.id.imgWorker);
        imgWorkerMobile= (ImageView) findViewById(R.id.imgWorkerMobile);
        imgCustomerMobile= (ImageView) findViewById(R.id.imgCustomerMobile);


        llSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day



                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(RequestDetailActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {



//                                    txtPickUpEarlistDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);


//                                startDate = txtPickUpEarlistDate.getText().toString().trim();

                            }
                        }, mYear, mMonth, mDay);
                // disable old date in calender
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        llSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
