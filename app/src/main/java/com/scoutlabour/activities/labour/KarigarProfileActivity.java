package com.scoutlabour.activities.labour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scoutlabour.R;
import com.scoutlabour.activities.AboutUsActivity;
import com.scoutlabour.activities.ContactUsActivity;
import com.scoutlabour.custom.PrefUtils;
import com.scoutlabour.model.labour.RegistrationLabourDetailModel;

public class KarigarProfileActivity extends AppCompatActivity {

    private LinearLayout llAboutUs,llLogOut,llContactUs;
    private TextView txtFullName,txtEmail,txtMobileNumber;
    private RegistrationLabourDetailModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karigar_profile);
        setToolbar();

        user=PrefUtils.getlabour(KarigarProfileActivity.this);
        txtFullName = (TextView) findViewById(R.id.txtFullName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtMobileNumber = (TextView) findViewById(R.id.txtMobileNumber);
        
        llAboutUs = (LinearLayout) findViewById(R.id.llAboutUs);
        llLogOut = (LinearLayout) findViewById(R.id.llLogOut);
        llContactUs = (LinearLayout) findViewById(R.id.llContactUs);


        llAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(KarigarProfileActivity.this,AboutUsActivity.class);
                startActivity(i);
            }
        });

        llContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(KarigarProfileActivity.this,ContactUsActivity.class);
                startActivity(i);
            }
        });


        llLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefUtils.clearCurrentlabour(KarigarProfileActivity.this);
                Intent i =new Intent(KarigarProfileActivity.this,LoginActivity.class);
                startActivity(i);
                finish();


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

    }
    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("My Profile");
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
