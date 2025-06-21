package com.example.medicalbooking.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.medicalbooking.R;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();
    }

    private void initUI() {
        ((TextView) findViewById(R.id.registerLink)).setOnClickListener(this);
        ((Button) findViewById(R.id.loginButton)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.registerLink) {
            nextActivity(RegistrationActivity.class);
        }
        else if(id == R.id.loginButton) {
//            nextActivity(ProfileActivity.class);
            nextActivity(BookApointmentActivity.class);
        }
    }
}
