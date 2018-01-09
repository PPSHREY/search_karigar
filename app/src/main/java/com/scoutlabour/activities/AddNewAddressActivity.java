package com.scoutlabour.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.scoutlabour.R;
import com.scoutlabour.custom.AppConstants;
import com.scoutlabour.custom.PostServiceCall;
import com.scoutlabour.custom.PrefUtils;
import com.scoutlabour.model.AddAddressResponseModel;
import com.scoutlabour.model.AddressDetailListModel;
import com.scoutlabour.model.AddressDetailModel;
import com.scoutlabour.model.UpdateAddressResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class AddNewAddressActivity extends AppCompatActivity {

    private EditText etFlatHouse, etColony, etLandmark, etCity, etPincode, etAddressNickName;
    private Spinner spState;
    private RadioButton rdbHome, rdbOffice, rdbOther;
    private RadioGroup rgbAddress;
    private LinearLayout llAddAddress,llAddressName;
    private String selectedLocation, selectedState;
    private boolean isEdit;
    private int selectedItemPosition;
    private TextView txtAddAddress;
    ArrayList<String> stateList;
    private ArrayList<AddressDetailModel> addressDetailModels;
    private AddressDetailListModel addressDetailListModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_address);

        rdbHome = (RadioButton) findViewById(R.id.rdbHome);
        rdbOffice = (RadioButton) findViewById(R.id.rdbOffice);
        rdbOther = (RadioButton) findViewById(R.id.rdbOther);

        rgbAddress= (RadioGroup) findViewById(R.id.rgbAddress);


        etAddressNickName = (EditText) findViewById(R.id.etAddressNickName);
        etFlatHouse = (EditText) findViewById(R.id.etFlatHouse);
        etColony = (EditText) findViewById(R.id.etColony);
        etLandmark = (EditText) findViewById(R.id.etLandmark);
        etCity = (EditText) findViewById(R.id.etCity);
        etPincode = (EditText) findViewById(R.id.etPincode);

        txtAddAddress = (TextView) findViewById(R.id.txtAddAddress);

        spState = (Spinner) findViewById(R.id.spState);

        selectedItemPosition = getIntent().getIntExtra("position", 0);
        isEdit = getIntent().getBooleanExtra("is_edit", false);

        try {
            addressDetailModels = PrefUtils.getAddressList(AddNewAddressActivity.this).addressDetailModelArrayList;
        } catch (Exception e) {
            e.printStackTrace();
            addressDetailModels = new ArrayList<AddressDetailModel>();
        }

        rgbAddress.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                if(rdbOther.isChecked()){
                    etAddressNickName.setVisibility(View.VISIBLE);
                }else if(rdbOffice.isChecked()){
                    etAddressNickName.setVisibility(View.GONE);
                }else if (rdbHome.isChecked()){
                    etAddressNickName.setVisibility(View.GONE);
                }

          }
        });

        llAddressName = (LinearLayout) findViewById(R.id.llAddressName);
        llAddAddress = (LinearLayout) findViewById(R.id.llAddAddress);
        llAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rdbOther.isChecked()){
                    if (etAddressNickName.getText().toString().trim().length() == 0){

                        Toast.makeText(AddNewAddressActivity.this, "Please Enter Address Nick Name", Toast.LENGTH_SHORT).show();
                    }
                    else   if (etFlatHouse.getText().toString().trim().length() == 0) {
                        Toast.makeText(AddNewAddressActivity.this, "Please Enter Flat / House / Floor/ Building ", Toast.LENGTH_SHORT).show();
                    } else if (etColony.getText().toString().trim().length() == 0) {
                        Toast.makeText(AddNewAddressActivity.this, "Please Enter  Colony / Street / Locality ", Toast.LENGTH_SHORT).show();
                    } else if (etLandmark.getText().toString().trim().length() == 0) {
                        Toast.makeText(AddNewAddressActivity.this, "Please Enter  Landmark ", Toast.LENGTH_SHORT).show();
                    } else if (etCity.getText().toString().trim().length() == 0) {
                        Toast.makeText(AddNewAddressActivity.this, "Please Enter  City / Village ", Toast.LENGTH_SHORT).show();
                    } else if (spState.getSelectedItemPosition() == 0) {
                        Toast.makeText(AddNewAddressActivity.this, "Please Select State ", Toast.LENGTH_SHORT).show();
                    } else if (etPincode.getText().toString().trim().length() == 0) {
                        Toast.makeText(AddNewAddressActivity.this, "Please Enter Pincode ", Toast.LENGTH_SHORT).show();
                    }else {
                        if(isEdit){
                            doPostOperationUpdate();
                        }else {
                            doPostNetworkOperation();
                        }
                    }

                }
           else   if (etFlatHouse.getText().toString().trim().length() == 0) {
                    Toast.makeText(AddNewAddressActivity.this, "Please Enter Flat / House / Floor/ Building ", Toast.LENGTH_SHORT).show();
                } else if (etColony.getText().toString().trim().length() == 0) {
                    Toast.makeText(AddNewAddressActivity.this, "Please Enter  Colony / Street / Locality ", Toast.LENGTH_SHORT).show();
                } else if (etLandmark.getText().toString().trim().length() == 0) {
                    Toast.makeText(AddNewAddressActivity.this, "Please Enter  Landmark ", Toast.LENGTH_SHORT).show();
                } else if (etCity.getText().toString().trim().length() == 0) {
                    Toast.makeText(AddNewAddressActivity.this, "Please Enter  City / Village ", Toast.LENGTH_SHORT).show();
                } else if (spState.getSelectedItemPosition() == 0) {
                    Toast.makeText(AddNewAddressActivity.this, "Please Select State ", Toast.LENGTH_SHORT).show();
                } else if (etPincode.getText().toString().trim().length() == 0) {
                    Toast.makeText(AddNewAddressActivity.this, "Please Enter Pincode ", Toast.LENGTH_SHORT).show();
                }else {
                   if(isEdit){
                       doPostOperationUpdate();
                  }else {
                        doPostNetworkOperation();
                  }
                }


            }
        });


        initCustomSpinner();
        setToolbar();
        if (isEdit) {
            if(addressDetailModels.get(selectedItemPosition).address_name.equalsIgnoreCase("Home")){
                rdbHome.setChecked(true);
            }else if(addressDetailModels.get(selectedItemPosition).address_name.equalsIgnoreCase("Office")){
                rdbOffice.setChecked(true);
            }else {
                rdbOther.setChecked(true);
            }
            rdbHome.setClickable(false);
            rdbOffice.setClickable(false);
            rdbOther.setClickable(false);
            txtAddAddress.setText("Update Address");
            etAddressNickName.setText(addressDetailModels.get(selectedItemPosition).address_name);
            etAddressNickName.setEnabled(false);
            etLandmark.setText(addressDetailModels.get(selectedItemPosition).address_3);
            etCity.setText(addressDetailModels.get(selectedItemPosition).city);
            etPincode.setText(addressDetailModels.get(selectedItemPosition).pincode);
            etColony.setText(addressDetailModels.get(selectedItemPosition).address_2);
            etFlatHouse.setText(addressDetailModels.get(selectedItemPosition).address_1);

            for (int i = 0; i < stateList.size(); i++) {
                if (stateList.get(i).equalsIgnoreCase(addressDetailModels.get(selectedItemPosition).state)) {
                    spState.setSelection(i);
                }
            }
            spState.setEnabled(false);


        }else {
            txtAddAddress.setText("Add Address");
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    private void doPostOperationUpdate() {

        if (isNetworkConnected()) {

            final ProgressDialog progressDialog = new ProgressDialog(AddNewAddressActivity.this);
            progressDialog.setMessage("Updating.....");
            progressDialog.setCancelable(false);
            progressDialog.show();




            JSONObject registrationObject = new JSONObject();

            try {



                if (rdbOther.isChecked())
                {
                    registrationObject.put("address_name",etAddressNickName.getText().toString().trim());
                }else if(rdbHome.isChecked()){
                    registrationObject.put("address_name","Home");
                }
                else if(rdbOffice.isChecked()){
                    registrationObject.put("address_name","Office");
                }
                registrationObject.put("address_1", etFlatHouse.getText().toString().trim());
                registrationObject.put("address_2", etColony.getText().toString().trim());
                registrationObject.put("address_3", etLandmark.getText().toString().trim());
                registrationObject.put("city", etCity.getText().toString().trim());
                registrationObject.put("state", selectedState);
                registrationObject.put("pincode", etPincode.getText().toString().trim());
                registrationObject.put("user_id", PrefUtils.getUser(AddNewAddressActivity.this).user_id);
                registrationObject.put("address_id", addressDetailModels.get(selectedItemPosition).address_id);






            } catch (JSONException e) {
                e.printStackTrace();
            }


            Log.e("update request_object",registrationObject+"");

            new PostServiceCall(AppConstants.UPDATE_MY_ADDRESS, registrationObject) {

                @Override
                public void response(String response) {
                    progressDialog.dismiss();

                    Log.e("response", response + "");


                    UpdateAddressResponseModel updateAddressResponseModel = new GsonBuilder().create().fromJson(response, UpdateAddressResponseModel.class);

                    if (updateAddressResponseModel.status.equalsIgnoreCase("1")) {
                        Toast.makeText(AddNewAddressActivity.this, updateAddressResponseModel.message + "", Toast.LENGTH_LONG).show();

                        finish();

                    } else {
                        Toast.makeText(AddNewAddressActivity.this, updateAddressResponseModel.message + "", Toast.LENGTH_LONG).show();
                    }


                }

                @Override
                public void error(String error) {
                    progressDialog.dismiss();
                    Log.e(" response...", error + "");
                }
            }.call();

        }else {
            Toast.makeText(AddNewAddressActivity.this, "Internet Connection  not Avaiable", Toast.LENGTH_SHORT).show();
        }

    }

    private void doPostNetworkOperation() {


        if (isNetworkConnected()) {

            final ProgressDialog progressDialog = new ProgressDialog(AddNewAddressActivity.this);
            progressDialog.setMessage("Submitting.....");
            progressDialog.setCancelable(false);
            progressDialog.show();
            JSONObject registrationObject = new JSONObject();
            try {
                if (rdbOther.isChecked())
                {
                    registrationObject.put("address_name",etAddressNickName.getText().toString().trim());
                }else if(rdbHome.isChecked()){
                    registrationObject.put("address_name","Home");
                }
                else if(rdbOffice.isChecked()){
                    registrationObject.put("address_name","Office");
                }
                registrationObject.put("address_1", etFlatHouse.getText().toString().trim());
                registrationObject.put("address_2", etColony.getText().toString().trim());
                registrationObject.put("address_3", etLandmark.getText().toString().trim());
                registrationObject.put("city", etCity.getText().toString().trim());
                registrationObject.put("state", selectedState);
                registrationObject.put("pincode", etPincode.getText().toString().trim());
//                registrationObject.put("user_id", "2");
                registrationObject.put("user_id", PrefUtils.getUser(AddNewAddressActivity.this).user_id);

                Log.e("request object", registrationObject+"");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("url main", AppConstants.ADD_ADDRESS);
            new PostServiceCall(AppConstants.ADD_ADDRESS, registrationObject) {

                @Override
                public void response(String response) {

                    progressDialog.dismiss();

                    Log.e("response", response + "");

                    AddAddressResponseModel addAddressResponseModel = new GsonBuilder().create().fromJson(response, AddAddressResponseModel.class);

                    if (addAddressResponseModel.status.equalsIgnoreCase("1")) {
                        Toast.makeText(AddNewAddressActivity.this, addAddressResponseModel.message + "", Toast.LENGTH_LONG).show();

                        finish();
                    } else {
                        Toast.makeText(AddNewAddressActivity.this, addAddressResponseModel.message + "", Toast.LENGTH_LONG).show();
                    }



                }


                @Override
                public void error(String error) {
                    progressDialog.dismiss();

                    Log.e(" response error...", error + "");
                }
            }.call();


        } else {
            Toast.makeText(AddNewAddressActivity.this, "Internet Connection  not Avaiable", Toast.LENGTH_SHORT).show();
        }


    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            if(isEdit){
                toolbar.setTitle("Update Address Details");
            }else {
                toolbar.setTitle("Add a new Address");
            }

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


    private void initCustomSpinner() {

        // Spinner Drop down elements
       stateList = new ArrayList<String>();
        stateList.add("Select State");
        stateList.add("Andhra Pradesh");
        stateList.add("Andaman and Nicobar Islands");
        stateList.add("Arunchal Pradesh");
        stateList.add("Assam");
        stateList.add("Bihar");
        stateList.add("Chhattisgarh");
        stateList.add("Chhandigarh");
        stateList.add("Delhi");
        stateList.add("Dadar and Nagar Haveli");
        stateList.add("Daman and Diu");
        stateList.add("Goa");
        stateList.add("Gujarat");
        stateList.add("Haryana");
        stateList.add("Himachal Pradesh");
        stateList.add("Jammu and Kashmir");
        stateList.add("Jharkhand");
        stateList.add("Karnataka");
        stateList.add("Kerala");
        stateList.add("Lakshadweep");
        stateList.add("Madhya Pradesh");
        stateList.add("Maharashtra");
        stateList.add("Manipur");
        stateList.add("Meghalaya");
        stateList.add("Mizoram");
        stateList.add("Nagaland");
        stateList.add("Odisha");
        stateList.add("Punjab");
        stateList.add("Puducherry");
        stateList.add("Rajasthan");
        stateList.add("Sikkim");
        stateList.add("Tamil Nadu");
        stateList.add("Telangana");
        stateList.add("Tripura");
        stateList.add("Uttar Pradesh");
        stateList.add("Uttarakhand");
        stateList.add("West Bengal");

        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(AddNewAddressActivity.this, stateList);
        spState.setAdapter(customSpinnerAdapter);
        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedState = parent.getItemAtPosition(position).toString();

//                Toast.makeText(parent.getContext(), "Android Custom Spinner Example Output..." + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

        private final Context activity;
        private ArrayList<String> asr;

        public CustomSpinnerAdapter(Context context, ArrayList<String> asr) {
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
            TextView txt = new TextView(AddNewAddressActivity.this);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(16);
            txt.setGravity(Gravity.CENTER_VERTICAL);
            txt.setText(asr.get(position));
            txt.setTextColor(Color.parseColor("#9C9C9C"));
            return txt;
        }

        public View getView(int i, View view, ViewGroup viewgroup) {
            TextView txt = new TextView(AddNewAddressActivity.this);
            txt.setGravity(Gravity.CENTER_VERTICAL);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(16);
            txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down, 0);
            txt.setText(asr.get(i));
            txt.setTextColor(Color.parseColor("#000000"));
            return txt;
        }

    }

}
