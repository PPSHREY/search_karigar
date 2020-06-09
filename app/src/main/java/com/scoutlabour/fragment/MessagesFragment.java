package com.scoutlabour.fragment;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.scoutlabour.activities.MyAddressActivity;
import com.scoutlabour.custom.AppConstants;
import com.scoutlabour.custom.PostServiceCall;
import com.scoutlabour.custom.PrefUtils;
import com.scoutlabour.custom.RVEmptyObserver;
import com.scoutlabour.model.NotificationDetailListModel;
import com.scoutlabour.model.NotificationDetailModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {


  private   RecyclerView recyclerViewMessages;
    private SwipeRefreshLayout swipe_refresh_layout;
    private ArrayList<NotificationDetailModel> filteredNotificationModel;
    NotificationDetailListModel notificationDetailList;
    JSONObject registrationObject;
    CustomAdapter customAdapter;
    View emptyView;

    //pagination logic
    View footerView;
    private boolean isServiceCalled = false;
    int lastProductId = -1;


    public MessagesFragment() {
        // Required empty public constructor
    }

    public static MessagesFragment newInstance() {

        return new MessagesFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertView = inflater.inflate(R.layout.fragment_messages, container, false);
        recyclerViewMessages = (RecyclerView) convertView.findViewById(R.id.recyclerViewMessages);
        recyclerViewMessages.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerViewMessages.setLayoutManager(layoutManager);
        emptyView   =  convertView.findViewById(R.id.empty_view);
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

            swipe_refresh_layout.setRefreshing(true);
            lastProductId = -1;

//

            registrationObject = new JSONObject();

            try {

                registrationObject.put("user_id", PrefUtils.getUser(getActivity()).user_id);
                registrationObject.put("page", lastProductId + "");


            } catch (JSONException e) {
                e.printStackTrace();
            }


            Log.e("registrationObject", registrationObject + "");

            new PostServiceCall(AppConstants.NOTIFICATIONS, registrationObject) {

                @Override
                public void response(String response) {

                    swipe_refresh_layout.setRefreshing(false);

                    Log.e("response", response + "");

                    notificationDetailList = new GsonBuilder().create().fromJson(response, NotificationDetailListModel.class);
                    filteredNotificationModel = notificationDetailList.notificationDetailModels;
                    customAdapter = new CustomAdapter(getActivity(), filteredNotificationModel);
                    recyclerViewMessages.setAdapter(customAdapter); // set the Adapter to RecyclerView
                    // set the emptyView in recycleview
                    customAdapter.registerAdapterDataObserver(new RVEmptyObserver(recyclerViewMessages, emptyView));






                }


                @Override
                public void error(String error) {
                    swipe_refresh_layout.setRefreshing(false);
                    Log.e(" response error...", error + "");
                }
            }.call();


        } else {
            Toast.makeText(getActivity(), "Internet Connection  not Avaiable", Toast.LENGTH_SHORT).show();

        }


    }
    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

        ArrayList<NotificationDetailModel> notificationList;
        Context context;


        private CustomAdapter(Context context, ArrayList<NotificationDetailModel> notificationList) {
            this.context = context;
            this.notificationList = notificationList;


        }
        @Override
        public CustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // infalte the item_dashboard Layout
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_item_list, parent, false);

            // set the view's size, margins, paddings and layout parameters
            CustomAdapter.MyViewHolder vh = new CustomAdapter.MyViewHolder(v); // pass the view to View Holder
            return vh;
        }

        @Override
        public void onBindViewHolder(CustomAdapter.MyViewHolder holder, final int position) {
            // set the data in items
            holder.txtTitle.setText(notificationList.get(position).title);
            holder.txtDateTime.setText(notificationList.get(position).date);
            holder.txtMessage.setText(notificationList.get(position).message);



//            //Pagination Logic start
            if (!isServiceCalled && lastProductId != (notificationDetailList.total)) {


                if (position == notificationList.size() - 2 || position == notificationList.size() - 3) {
                    isServiceCalled = true;
                    lastProductId = lastProductId + 1;
                    try {

                        registrationObject.put("user_id", PrefUtils.getUser(getActivity()).user_id);
//                        registrationObject.put("user_id", "1");
                        registrationObject.put("page",lastProductId+"");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Log.e("registrationObject", registrationObject + "");

//                    String completeURL = AppConstants.NOTIFICATIONS + (lastProductId);
//                    Log.e("complete URL", completeURL);

                    new PostServiceCall(AppConstants.NOTIFICATIONS, registrationObject) {

                        @Override
                        public void response(String response) {

                            swipe_refresh_layout.setRefreshing(false);
                            Log.e("response", response + "");
                            notificationDetailList = new GsonBuilder().create().fromJson(response, NotificationDetailListModel.class);
                            filteredNotificationModel = notificationDetailList.notificationDetailModels;
                            notificationList.addAll(notificationDetailList.notificationDetailModels);


                            //notifyDatasetchanged add data in listview
                            notifyDataSetChanged();


                            isServiceCalled = false;
                        }


                        @Override
                        public void error(String error) {
                            swipe_refresh_layout.setRefreshing(false);
                            isServiceCalled = false;
                            Log.e(" response...", error + "");
                        }
                    }.call();

                } else {

                }
            } else {

            }


            try {
                if (notificationList.size() == 0) {
//                    footerView.setVisibility(View.GONE);
                }
                if (lastProductId == (notificationDetailList.total)) {

//                    lvProfiles.removeFooterView(footerView);
                } else {


                    if (notificationList.size() == 0) {
//                        footerView.setVisibility(View.GONE);
                    } else {
//                        footerView.setVisibility(View.VISIBLE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
//            //pagination Logic End

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
            return notificationList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            // init the item_dashboard view's
            TextView txtTitle,txtDateTime,txtMessage;
            ImageView image;
            LinearLayout llMain;


            public MyViewHolder(View itemView) {
                super(itemView);

                // get the reference of item_dashboard view's
                txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
                txtDateTime = (TextView) itemView.findViewById(R.id.txtDateTime);
                txtMessage = (TextView) itemView.findViewById(R.id.txtMessage);


            }
        }
    }


}
