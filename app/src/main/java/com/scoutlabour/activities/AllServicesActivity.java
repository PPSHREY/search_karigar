package com.scoutlabour.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;
import com.scoutlabour.R;
import com.scoutlabour.custom.AppConstants;
import com.scoutlabour.custom.GetServiceCall;
import com.scoutlabour.custom.PostServiceCall;
import com.scoutlabour.custom.RVEmptyObserver;
import com.scoutlabour.model.SubCategoryDetailListModel;
import com.scoutlabour.model.SubCategoryDetailModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class AllServicesActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipe_refresh_layout;
    RecyclerView recyclerViewServices;
    private ArrayList<SubCategoryDetailModel> subCategoryDetailModels;
    SubCategoryDetailListModel subCategoryDetailListModel;
    CustomAdapter customAdapter;
    View emptyView;

    String id;
    String que="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_services);

        recyclerViewServices = (RecyclerView) findViewById(R.id.recyclerViewServices);
        recyclerViewServices.setHasFixedSize(true);
        recyclerViewServices.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(AllServicesActivity.this, 1);
        recyclerViewServices.setLayoutManager(layoutManager);
        emptyView   =  findViewById(R.id.empty_view);
        swipe_refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                customAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    private void doPostNetworkOperation() {

        if (isNetworkConnected()) {


            swipe_refresh_layout.setRefreshing(true);

            new GetServiceCall(AppConstants.ALL_SUB_CATEGORY_LIST, GetServiceCall.TYPE_JSONOBJECT) {

                @Override
                public void response(String response) {
                    swipe_refresh_layout.setRefreshing(false);
                    Log.e("  response", response + "");

                    subCategoryDetailListModel = new GsonBuilder().create().fromJson(response, SubCategoryDetailListModel.class);
                    subCategoryDetailModels = subCategoryDetailListModel.subCategoryDetailModels;

                    customAdapter = new CustomAdapter(AllServicesActivity.this, subCategoryDetailModels);
                    recyclerViewServices.setAdapter(customAdapter); // set the Adapter to RecyclerView
                    // set the emptyView in recycleview
                    customAdapter.registerAdapterDataObserver(new RVEmptyObserver(recyclerViewServices, emptyView));

//


                }

                @Override
                public void error(VolleyError error) {
                    swipe_refresh_layout.setRefreshing(false);
                    Log.e(" response...", error + "");
                }
            }.start();


        }

    }




    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>implements Filterable {

        private  ArrayList<SubCategoryDetailModel> subCategoryList;
        private  ArrayList<SubCategoryDetailModel> mFilteredList;
        Context context;


        public CustomAdapter(Context context, ArrayList<SubCategoryDetailModel> subCategoryList) {
            this.context = context;
            this.subCategoryList = subCategoryList;
            this.mFilteredList = subCategoryList;


        }
        @Override
        public CustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // infalte the item_dashboard Layout
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_list_item, parent, false);

            // set the view's size, margins, paddings and layout parameters
            CustomAdapter.MyViewHolder vh = new CustomAdapter.MyViewHolder(v); // pass the view to View Holder
            return vh;
        }

        @Override
        public void onBindViewHolder(CustomAdapter.MyViewHolder holder, final int position) {
            // set the data in items

            holder.txtServiceName.setText(mFilteredList.get(position).sub_category_name);
            Glide.with(AllServicesActivity.this).load(mFilteredList.get(position).sub_category_image).into(holder.imgServiceLogo);
            // implement setOnClickListener event on item_dashboard view.
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // open another activity on item_dashboard click
                    Intent i = new Intent(AllServicesActivity.this, RequestServiceActivity.class);
                    i.putExtra("subCategoryName",subCategoryList.get(position).sub_category_name);
                    i.putExtra("charges",subCategoryList.get(position).charges);
                    i.putExtra("serviceDetail",subCategoryList.get(position).service_details);
                    i.putExtra("position", position);
                    startActivity(i); // start Intent
                }
            });

        }

        @Override
        public int getItemCount() {
            return mFilteredList.size();
        }
        @Override
        public Filter getFilter() {

            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {

                    String charString = charSequence.toString();

                    if (charString.isEmpty()) {

                        mFilteredList = subCategoryList;
                    } else {

                        ArrayList<SubCategoryDetailModel> filteredList = new ArrayList<>();

                        for (SubCategoryDetailModel subCategoryDetail : subCategoryList) {

                            if (subCategoryDetail.sub_category_name.toLowerCase().contains(charString) ) {

                                filteredList.add(subCategoryDetail);
                            }
                        }

                        mFilteredList = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = mFilteredList;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    mFilteredList = (ArrayList<SubCategoryDetailModel>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            // init the item_dashboard view's
            TextView txtServiceName;
            ImageView imgServiceLogo;
            LinearLayout llDeleteAddress,llEditAddress;


            public MyViewHolder(View itemView) {
                super(itemView);

                // get the reference of item_dashboard view's
                txtServiceName= (TextView) itemView.findViewById(R.id.txtServiceName);
                imgServiceLogo= (ImageView) itemView.findViewById(R.id.imgServiceLogo);


            }
        }
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("All Services");
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
