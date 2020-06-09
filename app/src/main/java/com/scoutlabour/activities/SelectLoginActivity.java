package com.scoutlabour.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.scoutlabour.R;
import com.scoutlabour.activities.labour.*;

public class SelectLoginActivity extends AppCompatActivity {

    Button btnUser,btnLabour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login);

        btnLabour = findViewById(R.id.btnLabour);
        btnUser = findViewById(R.id.btnUser);

        btnLabour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SelectLoginActivity.this, com.scoutlabour.activities.labour.LoginActivity.class);
                startActivity(i);
            }
        });

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SelectLoginActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
