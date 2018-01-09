package com.scoutlabour.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scoutlabour.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InProgressFragment extends Fragment {


    public InProgressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertView= inflater.inflate(R.layout.fragment_in_progress, container, false);
        return convertView;
    }

}
