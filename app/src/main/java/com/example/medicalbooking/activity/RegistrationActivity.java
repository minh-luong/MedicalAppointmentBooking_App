package com.example.medicalbooking.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.medicalbooking.R;

public class RegistrationActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initUI();
    }

    private void initUI() {
        ((TextView) findViewById(R.id.loginLink)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.loginLink) {
            nextActivity(LoginActivity.class);
        }
    }
}
