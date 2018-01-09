package com.scoutlabour.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.scoutlabour.R;
import com.scoutlabour.custom.AppConstants;
import com.scoutlabour.custom.PostServiceCall;
import com.scoutlabour.custom.PrefUtils;
import com.scoutlabour.custom.RVEmptyObserver;
import com.scoutlabour.model.AddressDetailListModel;
import com.scoutlabour.model.AddressDetailModel;
import com.scoutlabour.model.RegistrationDetailModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class MyAddressActivity extends AppCompatActivity {


    private SwipeRefreshLayout swipe_refresh_layout;
    private RecyclerView recyclerViewAddresses;

    private FloatingActionButton fabBtnAddNewAddress;


    AddressDetailListModel addressDetailListModel;

    private ArrayList<AddressDetailModel> addressDetailModels;
    private CustomAdapter customAdapter;

    // Material Alert Dialog box
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;
    View emptyView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);

        fabBtnAddNewAddress = (FloatingActionButton) findViewById(R.id.fabBtnAddNewAddress);
        fabBtnAddNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyAddressActivity.this, AddNewAddressActivity.class);
                startActivity(i);
            }
        });
        swipe_refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        recyclerViewAddresses = (RecyclerView) findViewById(R.id.recyclerViewAddresses);
        recyclerViewAddresses.setHasFixedSize(true);
        recyclerViewAddresses.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MyAddressActivity.this, 1);
        recyclerViewAddresses.setLayoutManager(layoutManager);
        emptyView   =  findViewById(R.id.empty_view);

        setToolbar();
    }

    @Override
    public void onResume() {
        super.onResume();

        //swipe refresh logic start
        swipe_refresh_layout.post(new Runnable() {
            @Override
            public void run() {
                swipe_refresh_layout.setRefreshing(false);
                doPostNetworkOperation();
            }
        });

        swipe_refresh_layout.setColorSchemeResources(R.color.blue, R.color.blue, R.color.blue);
        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipe_refresh_layout.setRefreshing(true);
                        doPostNetworkOperation();
                    }
                }, 0);
            }
        });
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    private void doPostNetworkOperation() {


        if (isNetworkConnected()) {

//            final ProgressDialog progressDialog = new ProgressDialog(MyAddressActivity.this);
//            progressDialog.setMessage("");
//            progressDialog.setCancelable(false);
//            progressDialog.show();

            swipe_refresh_layout.setRefreshing(true);
            JSONObject registrationObject = new JSONObject();
            try {


//                registrationObject.put("user_id", "2");
                registrationObject.put("user_id", PrefUtils.getUser(MyAddressActivity.this).user_id);
                Log.e("request_object", PrefUtils.getUser(MyAddressActivity.this).user_id+"");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("url main", AppConstants.MY_ADDRESS_LIST);
            new PostServiceCall(AppConstants.MY_ADDRESS_LIST, registrationObject) {

                @Override
                public void response(String response) {
                    swipe_refresh_layout.setRefreshing(false);
//                    progressDialog.dismiss();

                    Log.e("response", response + "");

                     addressDetailListModel = new GsonBuilder().create().fromJson(response, AddressDetailListModel.class);
                    addressDetailModels = addressDetailListModel.addressDetailModelArrayList;

                    customAdapter = new CustomAdapter(MyAddressActivity.this, addressDetailModels);
                    Log.e("size", addressDetailModels.size() + "");
                    recyclerViewAddresses.setAdapter(customAdapter); // set the Adapter to RecyclerView
                    // set the emptyView in recycleview
                    customAdapter.registerAdapterDataObserver(new RVEmptyObserver(recyclerViewAddresses, emptyView));




                }


                @Override
                public void error(String error) {
                    swipe_refresh_layout.setRefreshing(false);
//                    progressDialog.dismiss();

                    Log.e(" response error...", error + "");
                }
            }.call();


        } else {
            Toast.makeText(MyAddressActivity.this, "Internet Connection  not Avaiable", Toast.LENGTH_SHORT).show();
//            recyclerViewDashan.setEmptyView(findViewById(R.id.llEmpty));
        }


    }


    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

        ArrayList<AddressDetailModel> addressList;
        Context context;


        public CustomAdapter(Context context, ArrayList<AddressDetailModel> addressList) {
            this.context = context;
            this.addressList = addressList;


        }

        @Override
        public CustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // infalte the item_dashboard Layout
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_addresses_item_list, parent, false);

            // set the view's size, margins, paddings and layout parameters
            CustomAdapter.MyViewHolder vh = new CustomAdapter.MyViewHolder(v); // pass the view to View Holder
            return vh;
        }

        @Override
        public void onBindViewHolder(CustomAdapter.MyViewHolder holder, final int position) {
            // set the data in items

            holder.txtAddressName.setText(addressList.get(position).address_name);
            holder.txtHouseNo.setText(addressList.get(position).address_1+", "+addressList.get(position).address_2);
            holder.txtLandMark.setText(addressList.get(position).address_3);
            holder.txtCityPincode.setText(addressList.get(position).city + ", " + addressList.get(position).pincode);
            holder.txtState.setText(addressList.get(position).state);

            holder.llEditAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PrefUtils.setAddressList(addressDetailListModel,MyAddressActivity.this);
                    Intent i = new Intent(MyAddressActivity.this, AddNewAddressActivity.class);
                    i.putExtra("is_edit", true);
                    i.putExtra("position", position);
                    startActivity(i);
                }
            });
            holder.llDeleteAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // create alert dialog builder
                    alertDialogBuilder = new AlertDialog.Builder(
                            MyAddressActivity.this);

                    // set title
                    alertDialogBuilder.setTitle("Are You Sure? You want Delete this Address.!");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage(" ")
                            .setCancelable(true)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, close
                                    // current activity
                        final ProgressDialog progressDialog;
                                    progressDialog = new ProgressDialog(MyAddressActivity.this);
                                    progressDialog.setMessage("Deleting...");
                                    progressDialog.show();
                                    JSONObject requestObjet = new JSONObject();
                                    try {

                                        requestObjet.put("address_id", addressList.get(position).address_id);
                                        requestObjet.put("user_id", PrefUtils.getUser(MyAddressActivity.this).user_id);
//                                        requestObjet.put("user_id", "2");


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    new PostServiceCall(AppConstants.DELETE_MY_ADDRESS, requestObjet) {

                                        @Override
                                        public void response(String response) {
                                            progressDialog.dismiss();
                                            Toast.makeText(MyAddressActivity.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();

                                            addressList.remove(position);
                                            customAdapter.notifyDataSetChanged();

                                        }

                                        @Override
                                        public void error(String error) {
                                            progressDialog.dismiss();
                                        }
                                    }.call();



                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
                                    dialog.cancel();
                                }
                            });
                    // create alert dialog
                    alertDialog = alertDialogBuilder.create();
                    alertDialog.show();


                }
            });
            // implement setOnClickListener event on item_dashboard view.
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // open another activity on item_dashboard click
//                    Intent i = new Intent(context, NewsDetailActivity.class);
////                i.putExtra("image", newsLogo.get(position)); // put image data in Intent
//                    i.putExtra("newsTitle", newsNames.get(position));
//                    i.putExtra("newsUrl", newsUrl.get(position));
//                    context.startActivity(i); // start Intent
                }
            });

        }

        @Override
        public int getItemCount() {
            return addressList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            // init the item_dashboard view's
            TextView txtAddressName, txtHouseNo, txtLandMark, txtCityPincode, txtState;
            ImageView image;
            LinearLayout llDeleteAddress, llEditAddress;


            public MyViewHolder(View itemView) {
                super(itemView);

                // get the reference of item_dashboard view's
                txtAddressName = (TextView) itemView.findViewById(R.id.txtAddressName);
                txtHouseNo = (TextView) itemView.findViewById(R.id.txtHouseNo);
                txtLandMark = (TextView) itemView.findViewById(R.id.txtLandMark);
                txtCityPincode = (TextView) itemView.findViewById(R.id.txtCityPincode);
                txtState = (TextView) itemView.findViewById(R.id.txtState);
                llDeleteAddress = (LinearLayout) itemView.findViewById(R.id.llDeleteAddress);
                llEditAddress = (LinearLayout) itemView.findViewById(R.id.llEditAddress);


            }
        }
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("My Addresses");
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
