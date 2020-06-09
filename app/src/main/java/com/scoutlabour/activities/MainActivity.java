package com.scoutlabour.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.scoutlabour.R;


public class MainActivity extends AppCompatActivity {

    private LinearLayout llCleaning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        llCleaning= (LinearLayout) findViewById(R.id.llCleaning);
        llCleaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(MainActivity.this,AllServicesTabViewActivity.class);
                startActivity(i);
            }
        });
    }
}
