package com.scoutlabour.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.VolleyError;
import com.google.gson.GsonBuilder;
import com.scoutlabour.R;
import com.scoutlabour.custom.AppConstants;
import com.scoutlabour.custom.BottomNavigationViewHelper;
import com.scoutlabour.custom.GetServiceCall;
import com.scoutlabour.custom.PrefUtils;
import com.scoutlabour.fragment.MessagesFragment;
import com.scoutlabour.fragment.OrdersStatusFragment;
import com.scoutlabour.fragment.ProfileFragment;
import com.scoutlabour.fragment.SearchFragment;
import com.scoutlabour.model.SubCategoryDetailListModel;

public class HomeActivity extends AppCompatActivity {
    SubCategoryDetailListModel subCategoryDetailListModel;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        doPostNetworkOperation();
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_search:
                                toolbar.setTitle("Search a service");
                                selectedFragment = SearchFragment.newInstance();
                                break;
                            case R.id.action_orders:
                                toolbar.setTitle("My Orders");
                                selectedFragment = OrdersStatusFragment.newInstance();
                                break;
                            case R.id.action_messages:
                                toolbar.setTitle("My Messages");
                                selectedFragment = MessagesFragment.newInstance();
                                break;
                            case R.id.action_profile:
                                toolbar.setTitle("My Profile");
                                selectedFragment = ProfileFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_frame, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });
        //        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_frame, SearchFragment.newInstance());
        transaction.commit();

//    Used to select an item programmatically
        bottomNavigationView.getMenu().getItem(0).setChecked(true);

        setToolbar();
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    private void doPostNetworkOperation() {

        if (isNetworkConnected()) {
            final ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this);
            progressDialog.setMessage("Loading.....");
            progressDialog.setCancelable(false);
            progressDialog.show();


            new GetServiceCall(AppConstants.ALL_SUB_CATEGORY_LIST, GetServiceCall.TYPE_JSONOBJECT) {

                @Override
                public void response(String response) {
                    progressDialog.dismiss();
                    Log.e("  response", response + "");

                    subCategoryDetailListModel = new GsonBuilder().create().fromJson(response, SubCategoryDetailListModel.class);
                    PrefUtils.setSubCategoryListDetail(subCategoryDetailListModel,HomeActivity.this);
                    Log.e("size_home",subCategoryDetailListModel.subCategoryDetailModels.size()+"");








//


                }

                @Override
                public void error(VolleyError error) {
                    progressDialog.dismiss();
                    Log.e(" response...", error + "");
                }
            }.start();


        }

    }
    private void setToolbar() {
        toolbar  = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Home");
            setSupportActionBar(toolbar);

        }
    }



}
