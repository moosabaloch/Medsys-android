package com.demo.medical.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.demo.medical.R;

public class SplashActivity extends AppCompatActivity {
    private Class activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //If Login Main else LoginActivity
        if (true) {
            activity = LoginActivity.class;
        } else {
            activity = MainActivity.class;
        }

        Handler h2 = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                proceedToMain();
            }
        };
        h2.postDelayed(run, 1500);


    }


    private void proceedToMain() {
        startActivity(new Intent(SplashActivity.this, activity));
        SplashActivity.this.finish();
    }
}
