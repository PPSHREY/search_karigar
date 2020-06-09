package com.scoutlabour.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.scoutlabour.R;
import com.scoutlabour.custom.AppConstants;
import com.scoutlabour.custom.PayPalConfig;
import com.scoutlabour.custom.PostServiceCall;
import com.scoutlabour.custom.PrefUtils;
import com.scoutlabour.model.AddAddressResponseModel;
import com.scoutlabour.model.AddressDetailListModel;
import com.scoutlabour.model.AddressDetailModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

public class RequestServiceActivity extends AppCompatActivity {

    //Payment Amount
    private String paymentAmount;

    //Paypal intent request code to track onActivityResult method
    public static final int PAYPAL_REQUEST_CODE = 123;


    //Paypal Configuration Object
    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PayPalConfig.PAYPAL_CLIENT_ID);

    private TextView txtAddressLabel,txtSubCategoryName, txtCategoryName, txtTotalRs, txtServiceDetail, txtServicedescription, txtAddress, txtProblemDetail,txtAddressName,txtHouseNo,txtLandMark,txtCityPincode,txtState,txtNewAddress;
    private LinearLayout llCancel, llConfirm, llAddressDetail;
    private Spinner spAddress;
    private String selectedAddress, selectedState;
    private ArrayList<AddressDetailModel> addressDetailModels;
    private AddressDetailListModel addressDetailListModel;
    private EditText etProblemDetail;


    private int selectedItemPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_service);
        txtNewAddress= (TextView) findViewById(R.id.txtAddress);
        txtNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(RequestServiceActivity.this,AddNewAddressActivity.class);
                startActivity(i);
            }
        });
        txtAddressLabel = (TextView) findViewById(R.id.txtAddressLabel);
        txtSubCategoryName = (TextView) findViewById(R.id.txtSubCategoryName);
        txtCategoryName = (TextView) findViewById(R.id.txtCategoryName);
        txtTotalRs = (TextView) findViewById(R.id.txtTotalRs);
        txtServiceDetail = (TextView) findViewById(R.id.txtServiceDetail);
        txtServicedescription = (TextView) findViewById(R.id.txtServicedescription);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtProblemDetail = (TextView) findViewById(R.id.txtProblemDetail);

        txtAddressName = (TextView) findViewById(R.id.txtAddressName);
        txtHouseNo = (TextView) findViewById(R.id.txtHouseNo);
        txtLandMark = (TextView) findViewById(R.id.txtLandMark);
        txtCityPincode = (TextView) findViewById(R.id.txtCityPincode);
        txtState = (TextView) findViewById(R.id.txtState);

        etProblemDetail = (EditText) findViewById(R.id.etProblemDetail);
        selectedItemPosition = getIntent().getIntExtra("position", 0);

        txtTotalRs.setText("₹ "+getIntent().getStringExtra("charges"));
//        txtSubCategoryName.setText(getIntent().getStringExtra("subCategoryName"));
        txtCategoryName.setText(getIntent().getStringExtra("subCategoryName"));
        txtServicedescription.setText(getIntent().getStringExtra("serviceDetail"));

            llAddressDetail = (LinearLayout) findViewById(R.id.llAddressDetail);
        llCancel = (LinearLayout) findViewById(R.id.llCancel);
        llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestServiceActivity.this.finish();

            }
        });
        llConfirm = (LinearLayout) findViewById(R.id.llConfirm);

        llConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if (addressDetailListModel.addressDetailModelArrayList.size()==0) {

                    Toast.makeText(RequestServiceActivity.this, "Please Add Address ", Toast.LENGTH_SHORT).show();
                } else if (etProblemDetail.getText().toString().trim().length() == 0) {

                   Toast.makeText(RequestServiceActivity.this, "Please Enter the Problem Details", Toast.LENGTH_SHORT).show();
                }else {
//                   doPostNetworkOperation();

                   getPayment();
               }
            }
        });

        spAddress = (Spinner) findViewById(R.id.spAddress);



        setToolbar();
    }

    private void getPayment() {
        //Getting the amount from editText
        paymentAmount = getIntent().getStringExtra("charges");

        //Creating a paypalpayment
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(paymentAmount)), "USD", getIntent().getStringExtra("subCategoryName")+" Charges",
                PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);

                        //Starting a new activity for the payment details and also putting the payment details with intent
                        startActivity(new Intent(this, ConfirmationActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", paymentAmount)
                                .putExtra("categoryId", getIntent().getStringExtra("categoryId"))
                                .putExtra("subCategoryId", getIntent().getStringExtra("subCategoryId"))
                                .putExtra("address_id", addressDetailModels.get(spAddress.getSelectedItemPosition()).address_id)
                                .putExtra("problem_explaination", etProblemDetail.getText().toString().trim()));
                        finish();

                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        getAddresses();
    }

    private void getAddresses(){
        if (isNetworkConnected()) {


            JSONObject registrationObject = new JSONObject();

            try {

                registrationObject.put("user_id", PrefUtils.getUser(RequestServiceActivity.this).user_id);
                Log.e("request_object", PrefUtils.getUser(RequestServiceActivity.this).user_id + "");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.e("registrationObject", registrationObject + "");

            new PostServiceCall(AppConstants.MY_ADDRESS_LIST, registrationObject) {

                @Override
                public void response(String response) {
//                    progressDialog.dismiss();

                    Log.e("  response", response + "");


                    addressDetailListModel = new GsonBuilder().create().fromJson(response, AddressDetailListModel.class);
                    addressDetailModels = addressDetailListModel.addressDetailModelArrayList;

                    Log.e("size",addressDetailModels.size()+"");
                    addressDetailModels = new ArrayList<AddressDetailModel>();
//                    addressDetailModels.add(new AddressDetailModel("Select Address"));
                    try {

                        for (int i = 0; i < addressDetailListModel.addressDetailModelArrayList.size(); i++) {
                            addressDetailModels.add(addressDetailListModel.addressDetailModelArrayList.get(i));

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(addressDetailListModel.addressDetailModelArrayList.size()==0)
                    {
                        txtNewAddress.setVisibility(View.VISIBLE);
                        txtAddressLabel.setText("No Addresses Added Yet...!");
                        spAddress.setVisibility(View.GONE);
//                        Toast.makeText(RequestServiceActivity.this, "Please Add Your Address First", Toast.LENGTH_SHORT).show();
                    }else {
                        txtNewAddress.setVisibility(View.GONE);
                        txtAddressLabel.setText("Address");
                        spAddress.setVisibility(View.VISIBLE);
                        initCustomSpinner();

                    }
                }


                @Override
                public void error(String error) {
//                    progressDialog.dismiss();
                    Log.e(" response...", error + "");
                }
            }.call();

        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


    private void initCustomSpinner() {


        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(RequestServiceActivity.this, addressDetailModels);
        spAddress.setAdapter(customSpinnerAdapter);
        spAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                selectedAddress = parent.getItemAtPosition(position).toString();
                 selectedAddress=addressDetailModels.get(spAddress.getSelectedItemPosition()).address_name;
                Log.e("item",selectedAddress+"");
//                registrationObject.put("category_id", category.get(spCategory.getSelectedItemPosition()).getId());


                llAddressDetail.setVisibility(View.VISIBLE);
                try {
                    txtAddressName.setText(addressDetailModels.get(spAddress.getSelectedItemPosition()).address_name);
                    txtHouseNo.setText(addressDetailModels.get(spAddress.getSelectedItemPosition()).address_1+", "+addressDetailModels.get(spAddress.getSelectedItemPosition()).address_2);
                    txtLandMark.setText(addressDetailModels.get(spAddress.getSelectedItemPosition()).address_3);
                    txtCityPincode.setText(addressDetailModels.get(spAddress.getSelectedItemPosition()).city + ", " + addressDetailModels.get(spAddress.getSelectedItemPosition()).pincode);
                    txtState.setText(addressDetailModels.get(spAddress.getSelectedItemPosition()).state);
                } catch (Exception e) {
                    e.printStackTrace();
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

        private final Context activity;
        private ArrayList<AddressDetailModel> asr;

        public CustomSpinnerAdapter(Context context, ArrayList<AddressDetailModel> asr) {
            this.asr = asr;
            activity = context;
        }


        public int getCount() {
            return asr.size();
        }

        public Object getItem(int i) {
            return asr.get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }


        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView txt = new TextView(RequestServiceActivity.this);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(16);
            txt.setGravity(Gravity.CENTER);
            txt.setText(asr.get(position).address_name);
            txt.setTextColor(Color.parseColor("#000000"));
            return txt;


        }

        public View getView(int i, View view, ViewGroup viewgroup) {
            TextView txt = new TextView(RequestServiceActivity.this);
            txt.setGravity(Gravity.CENTER);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(16);
            txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down, 0);
            txt.setText(asr.get(i).address_name);
            txt.setTextColor(Color.parseColor("#000000"));
            return txt;
        }



    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Request a Service");
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
