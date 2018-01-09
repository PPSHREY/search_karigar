package com.scoutlabour.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.scoutlabour.R;
import com.scoutlabour.activities.AllServicesActivity;
import com.scoutlabour.activities.AllServicesTabViewActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private LinearLayout llAllService,llCleaning,llAcService,llCarpenterWork,llMasonryWork,llPaintingWork,llPlumbingWork,llElectricalWork;


    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {

        return new SearchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertView = inflater.inflate(R.layout.fragment_search, container, false);
        setHasOptionsMenu(true);

        llAllService = (LinearLayout) convertView.findViewById(R.id.llAllService);
        llCleaning = (LinearLayout) convertView.findViewById(R.id.llCleaning);
        llAcService = (LinearLayout) convertView.findViewById(R.id.llAcService);
        llCarpenterWork = (LinearLayout) convertView.findViewById(R.id.llCarpenterWork);
        llMasonryWork = (LinearLayout) convertView.findViewById(R.id.llMasonryWork);
        llPaintingWork = (LinearLayout) convertView.findViewById(R.id.llPaintingWork);
        llPlumbingWork = (LinearLayout) convertView.findViewById(R.id.llPlumbingWork);
        llElectricalWork = (LinearLayout) convertView.findViewById(R.id.llElectricalWork);

        llAllService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getActivity(),AllServicesActivity.class);
                startActivity(i);
            }
        });

        llCleaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getActivity(),AllServicesTabViewActivity.class);
                i.putExtra("tabPosition",0);
                startActivity(i);
            }
        });

        llCarpenterWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getActivity(),AllServicesTabViewActivity.class);
                i.putExtra("tabPosition",1);
                startActivity(i);
            }
        });
        llMasonryWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getActivity(),AllServicesTabViewActivity.class);
                i.putExtra("tabPosition",2);
                startActivity(i);
            }
        });
        llPaintingWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getActivity(),AllServicesTabViewActivity.class);
                i.putExtra("tabPosition",3);
                startActivity(i);
            }
        });
        llPlumbingWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getActivity(),AllServicesTabViewActivity.class);
                i.putExtra("tabPosition",4);
                startActivity(i);
            }
        });
        llElectricalWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getActivity(),AllServicesTabViewActivity.class);
                i.putExtra("tabPosition",5);
                startActivity(i);
            }
        });
        llAcService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getActivity(),AllServicesTabViewActivity.class);
                i.putExtra("tabPosition",6);
                startActivity(i);
            }
        });
        return convertView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_search_icon, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search_icon:
                Intent i =new Intent(getActivity(),AllServicesActivity.class);
                startActivity(i);



                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
