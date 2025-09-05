package com.example.medicalbooking.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.medicalbooking.R;

public class DoctorHomeActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_doctor);

        initUI();
    }

    private void initUI() {
        ((LinearLayout) findViewById(R.id.upcomingAppointmentsLayout)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.mapLayout)).setOnClickListener(this);
        ((ConstraintLayout) findViewById(R.id.notificationIconLayout)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.settingsIcon)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.profileIcon)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.upcomingAppointmentsLayout) {
            nextActivity(UpcomingAppointmentsActivity.class);
        }
        else if(id == R.id.medicalHistoryLayout) {
            nextActivity(TreatmentHistoryActivity.class);
        }
        else if(id == R.id.mapLayout) {
            nextActivity(HospitalLocatorActivity.class);
        }
        else if(id == R.id.notificationIconLayout) {
            nextActivity(NotificationsActivity.class);
        }
        else if(id == R.id.settingsIcon) {
            nextActivity(SettingsActivity.class);
        }
        else if(id == R.id.profileIcon) {
            nextActivity(ProfileActivity.class);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

    }
}
