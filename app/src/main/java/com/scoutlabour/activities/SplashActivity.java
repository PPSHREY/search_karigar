package com.scoutlabour.activities;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.scoutlabour.R;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new CountDownTimer(3000,1000){

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Intent i=new Intent(SplashActivity.this,SelectLoginActivity.class);
                startActivity(i);
                finish();
            }
        }.start();
    }
}
