package com.example.medicalbooking.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.medicalbooking.R;
import com.google.firebase.auth.FirebaseAuth;

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

    private void logout() {
        SharedPreferences prefs = getSharedPreferences("medicalbooking", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();

        FirebaseAuth.getInstance().signOut();

        nextActivityNoBack(LoginActivity.class);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.backArrow) {
            this.finish();
        }
        else if(id == R.id.logoutButton) {
            logout();
        }
    }
}
