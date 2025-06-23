package com.example.medicalbooking.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.medicalbooking.R;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initUI();
    }

    private void initUI() {
        ((LinearLayout) findViewById(R.id.bookAppointmentLayout)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.upcomingAppointmentsLayout)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.medicalHistoryLayout)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.medicationReminderLayout)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.mapLayout)).setOnClickListener(this);
        ((ConstraintLayout) findViewById(R.id.notificationIconLayout)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.bookAppointmentLayout) {
            nextActivity(BookAppointmentActivity.class);
        }
        else if(id == R.id.upcomingAppointmentsLayout) {
            nextActivity(UpcomingAppointmentsActivity.class);
        }
        else if(id == R.id.medicalHistoryLayout) {
            nextActivity(MedicalHistoryActivity.class);
        }
        else if(id == R.id.medicationReminderLayout) {
            nextActivity(MedicationReminderActivity.class);
        }
        else if(id == R.id.mapLayout) {
            nextActivity(HospitalLocatorActivity.class);
        }
        else if(id == R.id.notificationIconLayout) {
            nextActivity(NotificationsActivity.class);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

    }
}
