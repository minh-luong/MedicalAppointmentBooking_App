package com.example.medicalbooking.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.medicalbooking.R;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {

    private ImageView backArrow;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initUI();
    }

    private void initUI() {
        backArrow = findViewById(R.id.backArrow);
        logoutButton = findViewById(R.id.logoutButton);

        backArrow.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.backArrow) {
            this.finish();
        }
        else if(id == R.id.logoutButton) {
            nextActivity(LoginActivity.class);
            this.finish();
        }
    }
}
