package com.example.medicalbooking.activity;

import android.os.Bundle;

import com.example.medicalbooking.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                nextActivity(LoginActivity.class);
            }
        }, 2000);
    }
}