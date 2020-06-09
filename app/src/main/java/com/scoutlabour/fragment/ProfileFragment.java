package com.scoutlabour.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scoutlabour.R;
import com.scoutlabour.activities.AboutUsActivity;
import com.scoutlabour.activities.ChangePasswordActivity;
import com.scoutlabour.activities.ContactUsActivity;
import com.scoutlabour.activities.LoginActivity;
import com.scoutlabour.activities.MyAddressActivity;
import com.scoutlabour.custom.PrefUtils;
import com.scoutlabour.model.RegistrationDetailModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private LinearLayout llMyAddress,llChangePassword,llAboutUs,llShareApp,llRateApp,llLogOut,llContactUs;
    private TextView txtFullName,txtEmail,txtMobileNumber;
    private RegistrationDetailModel user;
    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {

        return new ProfileFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertView = inflater.inflate(R.layout.fragment_profile, container, false);
        user=PrefUtils.getUser(getActivity());
        txtFullName = (TextView) convertView.findViewById(R.id.txtFullName);
        txtEmail = (TextView) convertView.findViewById(R.id.txtEmail);
        txtMobileNumber = (TextView) convertView.findViewById(R.id.txtMobileNumber);


        llMyAddress = (LinearLayout) convertView.findViewById(R.id.llMyAddress);
        llChangePassword = (LinearLayout) convertView.findViewById(R.id.llChangePassword);
        llAboutUs = (LinearLayout) convertView.findViewById(R.id.llAboutUs);
        llShareApp = (LinearLayout) convertView.findViewById(R.id.llShareApp);
        llRateApp = (LinearLayout) convertView.findViewById(R.id.llRateApp);
        llLogOut = (LinearLayout) convertView.findViewById(R.id.llLogOut);
        llContactUs = (LinearLayout) convertView.findViewById(R.id.llContactUs);

        llMyAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getActivity(),MyAddressActivity.class);
                startActivity(i);
            }
        });

        llChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getActivity(),ChangePasswordActivity.class);
                startActivity(i);
            }
        });
        llAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getActivity(),AboutUsActivity.class);
                startActivity(i);
            }
        });

        llContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getActivity(),ContactUsActivity.class);
                startActivity(i);
            }
        });

        llShareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareApp();

            }
        });
        llRateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //rateApp();
            }
        });

        llLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefUtils.clearCurrentUser(getActivity());
                Intent i =new Intent(getActivity(),LoginActivity.class);
                startActivity(i);
                getActivity().finish();


            }
        });

        try {

            txtMobileNumber.setText("+91 "+user.mobile);
            txtFullName.setText(user.name);
            txtEmail.setText(user.email);
//            Glide.with(HomeActivity.this).load(user.getProfile_pic()).into(imgProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    private void rateApp() {
        final String appPackageName = getActivity().getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    private void shareApp() {

//        String shareBody = getString(R.string.download_app);
        String shareBody = "Download Search Karigar App";
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.main_appshare)));
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("My Profile");
    }


}
