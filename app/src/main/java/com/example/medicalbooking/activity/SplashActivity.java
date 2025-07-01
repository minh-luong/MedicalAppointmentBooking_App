package com.example.medicalbooking.activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.medicalbooking.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        checkLoggedIn();
    }

    private void checkLoggedIn() {
        SharedPreferences prefs = getSharedPreferences("medicalbooking", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);

        if(isLoggedIn) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    nextActivityNoBack(HomeActivity.class);
                }
            }, 1000);
        }
        else {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    nextActivity(LoginActivity.class);
                }
            }, 2000);
        }
    }
}