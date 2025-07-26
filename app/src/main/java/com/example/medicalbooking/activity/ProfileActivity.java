package com.example.medicalbooking.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medicalbooking.R;
import com.example.medicalbooking.factory.Factory;
import com.example.medicalbooking.model.User;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {

    private ImageView backArrow;
    private Button logoutButton;
    private TextView fullNameText, emailText, phoneText, addressText, genderText, dobText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initUI();
    }

    private void initUI() {
        // user information
        fullNameText = findViewById(R.id.fullNameText);
        emailText = findViewById(R.id.emailText);
        phoneText = findViewById(R.id.phoneText);
        addressText = findViewById(R.id.addressText);
        genderText = findViewById(R.id.genderText);
        dobText = findViewById(R.id.dobText);

        User user = Factory.getCurrentUser();
        fullNameText.setText(user.getFullName());
        emailText.setText(user.getEmail());
        phoneText.setText(user.getPhoneNumber());
        addressText.setText(user.getAddress());
        genderText.setText(user.getGender());

        // Format and set date of birth
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            inputFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));

            SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            Date dob = inputFormat.parse(user.getDateOfBirth());
            dobText.setText(outputFormat.format(dob));
        } catch (Exception e) {
            Log.e("ProfileActivity", "Get user infor error = " + e.getMessage());
            dobText.setText("N/A");
        }

        // button
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
