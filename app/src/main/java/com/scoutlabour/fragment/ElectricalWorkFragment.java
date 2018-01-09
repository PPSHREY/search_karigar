package com.scoutlabour.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;
import com.scoutlabour.R;
import com.scoutlabour.activities.RequestServiceActivity;
import com.scoutlabour.custom.AppConstants;
import com.scoutlabour.custom.GetServiceCall;
import com.scoutlabour.custom.PrefUtils;
import com.scoutlabour.custom.RVEmptyObserver;
import com.scoutlabour.model.SubCategoryDetailListModel;
import com.scoutlabour.model.SubCategoryDetailModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ElectricalWorkFragment extends Fragment {

    RecyclerView recyclerViewElectricalWork;
    private ArrayList<SubCategoryDetailModel> subCategoryDetailModels;
    private ArrayList<SubCategoryDetailModel> filterSubCategoryDetailModels;
    CustomAdapter customAdapter;
    View emptyView;

    public ElectricalWorkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        
        View convertView= inflater.inflate(R.layout.fragment_electrical_work, container, false);
        recyclerViewElectricalWork = (RecyclerView) convertView.findViewById(R.id.recyclerViewElectricalWork);
        recyclerViewElectricalWork.setHasFixedSize(true);
        recyclerViewElectricalWork.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerViewElectricalWork.setLayoutManager(layoutManager);
        recyclerViewElectricalWork.setItemAnimator(new DefaultItemAnimator());
        emptyView   =  convertView.findViewById(R.id.empty_view);
        try {
            subCategoryDetailModels = PrefUtils.getSubCategoryListDetail(getActivity()).subCategoryDetailModels;
        } catch (Exception e) {
            e.printStackTrace();

        }
        filterSubCategoryDetailModels=new ArrayList<SubCategoryDetailModel>();
        for(int i=0;i<subCategoryDetailModels.size();i++)
        {
            if(subCategoryDetailModels.get(i).category_id.equalsIgnoreCase("6")) {

                filterSubCategoryDetailModels.add(subCategoryDetailModels.get(i));


            }

        }
        Log.e("size_filter",filterSubCategoryDetailModels.size()+"");
        customAdapter = new CustomAdapter(getActivity(), filterSubCategoryDetailModels);
        recyclerViewElectricalWork.setAdapter(customAdapter); // set the Adapter to RecyclerView
        // set the emptyView in recycleview
        customAdapter.registerAdapterDataObserver(new RVEmptyObserver(recyclerViewElectricalWork, emptyView));


        return convertView;
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

        ArrayList<SubCategoryDetailModel> subCategoryList;
        Context context;


        public CustomAdapter(Context context, ArrayList<SubCategoryDetailModel> subCategoryList) {
            this.context = context;
            this.subCategoryList = subCategoryList;


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


            holder.txtServiceName.setText(subCategoryList.get(position).sub_category_name);
            Glide.with(getActivity()).load(subCategoryList.get(position).sub_category_image).into(holder.imgServiceLogo);


            // implement setOnClickListener event on item_dashboard view.
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // open another activity on item_dashboard click
                    Intent i = new Intent(getActivity(), RequestServiceActivity.class);
                    i.putExtra("subCategoryName",subCategoryList.get(position).sub_category_name);
                    i.putExtra("charges",subCategoryList.get(position).charges);
                    i.putExtra("serviceDetail",subCategoryList.get(position).service_details);
                    i.putExtra("categoryId",subCategoryList.get(position).category_id);
                    i.putExtra("subCategoryId",subCategoryList.get(position).sub_category_id);
                    i.putExtra("position", position);
                    startActivity(i); // start Intent
                }
            });

        }

        @Override
        public int getItemCount() {
            return subCategoryList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            // init the item_dashboard view's
            TextView txtServiceName;
            ImageView imgServiceLogo;
            LinearLayout llMain;


            public MyViewHolder(View itemView) {
                super(itemView);

                // get the reference of item_dashboard view's
                txtServiceName= (TextView) itemView.findViewById(R.id.txtServiceName);
                imgServiceLogo= (ImageView) itemView.findViewById(R.id.imgServiceLogo);


            }
        }
    }

}
