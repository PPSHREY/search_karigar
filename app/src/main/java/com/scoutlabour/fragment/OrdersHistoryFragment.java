package com.scoutlabour.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.scoutlabour.R;
import com.scoutlabour.activities.HistoryRequestDetailActivity;
import com.scoutlabour.activities.MyAddressActivity;
import com.scoutlabour.custom.AppConstants;
import com.scoutlabour.custom.PostServiceCall;
import com.scoutlabour.custom.PrefUtils;
import com.scoutlabour.custom.RVEmptyObserver;
import com.scoutlabour.model.NewRequestDetailListModel;
import com.scoutlabour.model.NewRequestDetailModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersHistoryFragment extends Fragment {

    RecyclerView recyclerViewRequest;
    private ArrayList<NewRequestDetailModel> newRequestDetailModels;
    NewRequestDetailListModel newRequestDetailListModel;
    CustomAdapter customAdapter;
    View emptyView;
    JSONObject registrationObject;
    ProgressDialog progressDialog;
    private SwipeRefreshLayout swipe_refresh_layout;
    //pagination logic
    View footerView;
    private boolean isServiceCalled = false;
    int lastProductId = -1;
    public OrdersHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View convertView= inflater.inflate(R.layout.fragment_orders_history, container, false);

        recyclerViewRequest = (RecyclerView) convertView.findViewById(R.id.recyclerViewRequest);
        recyclerViewRequest.setHasFixedSize(true);
        recyclerViewRequest.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerViewRequest.setLayoutManager(layoutManager);

        swipe_refresh_layout = (SwipeRefreshLayout) convertView.findViewById(R.id.swipe_refresh_layout);

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

        //         //swipe refresh logic end
        return convertView;
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    private void doPostNetworkOperation() {

        if (isNetworkConnected()) {

//            progressDialog = new ProgressDialog(getActivity());
//            progressDialog.setMessage("Loading.....");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
            swipe_refresh_layout.setRefreshing(true);

            lastProductId = -1;

//

            registrationObject = new JSONObject();

            try {
//                registrationObject.put("user_id", "2");
                registrationObject.put("user_id", PrefUtils.getUser(getActivity()).user_id);
                registrationObject.put("page", lastProductId + "");


            } catch (JSONException e) {
                e.printStackTrace();
            }


            Log.e("registrationObject", registrationObject + "");

            new PostServiceCall(AppConstants.ORDERS_LIST_USER_ASSIGNED, registrationObject) {

                @Override
                public void response(String response) {
//                    progressDialog.dismiss();
                    swipe_refresh_layout.setRefreshing(false);
                    Log.e("  response", response + "");

                    newRequestDetailListModel = new GsonBuilder().create().fromJson(response, NewRequestDetailListModel.class);
                    newRequestDetailModels = newRequestDetailListModel.newRequestDetailModelArrayList;

                    customAdapter = new CustomAdapter(getActivity(), newRequestDetailModels);
                    recyclerViewRequest.setAdapter(customAdapter); // set the Adapter to RecyclerView
                    // set the emptyView in recycleview
                    customAdapter.registerAdapterDataObserver(new RVEmptyObserver(recyclerViewRequest, emptyView));





                }


                @Override
                public void error(String error) {
                    swipe_refresh_layout.setRefreshing(false);
//                    progressDialog.dismiss();
                    Log.e(" response error...", error + "");
                }
            }.call();


        } else {
            Toast.makeText(getActivity(), "Internet Connection  not Avaiable", Toast.LENGTH_SHORT).show();

        }
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

        ArrayList<NewRequestDetailModel> newRequestList;
        Context context;


        public CustomAdapter(Context context, ArrayList<NewRequestDetailModel> newRequestList) {
            this.context = context;
            this.newRequestList = newRequestList;


        }
        @Override
        public CustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // infalte the item_dashboard Layout
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.requests_item_list, parent, false);

            // set the view's size, margins, paddings and layout parameters
            CustomAdapter.MyViewHolder vh = new CustomAdapter.MyViewHolder(v); // pass the view to View Holder
            return vh;
        }

        @Override
        public void onBindViewHolder(CustomAdapter.MyViewHolder holder, final int position) {
            // set the data in items

            holder.txtProblemExp.setText(newRequestList.get(position).problem_explaination);
            holder.txtCategoryName.setText(newRequestList.get(position).category_name);
            holder.txtSubCategoryName.setText(newRequestList.get(position).sub_category_name);
            holder.txtRequestNumber.setText("Request No: "+newRequestList.get(position).order_id);

            holder.imgCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String number = newRequestList.get(position).user_mobile+"";
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + number));
                    startActivity(callIntent);
                }
            });

//            //Pagination Logic start
            if (!isServiceCalled && lastProductId != (newRequestDetailListModel.total)) {


                if (position == newRequestList.size() - 2 || position == newRequestList.size() - 3) {
                    isServiceCalled = true;
                    lastProductId = lastProductId + 1;
                    try {

//                        registrationObject.put("user_id", "2");
                        registrationObject.put("user_id", PrefUtils.getUser(getActivity()).user_id);
                        registrationObject.put("page", lastProductId + "");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Log.e("registrationObject", registrationObject + "");


                    new PostServiceCall(AppConstants.ORDERS_LIST_USER_ASSIGNED, registrationObject) {

                        @Override
                        public void response(String response) {
                            progressDialog.dismiss();


                            Log.e("response", response + "");

                            newRequestDetailListModel = new GsonBuilder().create().fromJson(response, NewRequestDetailListModel.class);
                            newRequestDetailModels = newRequestDetailListModel.newRequestDetailModelArrayList;
                            newRequestList.addAll(newRequestDetailListModel.newRequestDetailModelArrayList);



                            //notifyDatasetchanged add data in listview
                            notifyDataSetChanged();


                            isServiceCalled = false;
                        }


                        @Override
                        public void error(String error) {
                            progressDialog.dismiss();
                            isServiceCalled = false;
                            Log.e(" response...", error + "");
                        }
                    }.call();

                } else {

                }
            } else {

            }


            try {
                if (newRequestList.size() == 0) {
//                    footerView.setVisibility(View.GONE);
                }
                if (lastProductId == (newRequestDetailListModel.total)) {

//                    lvProfiles.removeFooterView(footerView);
                } else {


                    if (newRequestList.size() == 0) {
//                        footerView.setVisibility(View.GONE);
                    } else {
//                        footerView.setVisibility(View.VISIBLE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
//            //pagination Logic End

//            // implement setOnClickListener event on item_dashboard view.
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // open another activity on item_dashboard click
                    PrefUtils.setRequestListDetail(newRequestDetailListModel,getActivity());
                    Intent i =new Intent(getActivity(), HistoryRequestDetailActivity.class);
                    i.putExtra("position",position);
                    startActivity(i);
                }
            });

        }

        @Override
        public int getItemCount() {
            return newRequestList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            // init the item_dashboard view's
            TextView txtRequestNumber,txtProblemExp,txtCategoryName,txtSubCategoryName;
            ImageView imgCall;


            public MyViewHolder(View itemView) {
                super(itemView);

                // get the reference of item_dashboard view's
                txtRequestNumber = (TextView) itemView.findViewById(R.id.txtRequestNumber);
                txtSubCategoryName = (TextView) itemView.findViewById(R.id.txtSubCategoryName);
                txtCategoryName = (TextView) itemView.findViewById(R.id.txtCategoryName);
                txtProblemExp = (TextView) itemView.findViewById(R.id.txtProblemExp);
                imgCall = (ImageView) itemView.findViewById(R.id.imgCall);



            }
        }
    }

}
