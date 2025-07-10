package com.example.medicalbooking.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.medicalbooking.R;
import com.example.medicalbooking.factory.Factory;
import com.example.medicalbooking.utils.HttpRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

import java.util.HashMap;

public class RegistrationActivity extends BaseActivity implements View.OnClickListener {

    private EditText fullNameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private TextView loginLink;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        initUI();
    }

    private void initUI() {
        fullNameEditText = findViewById(R.id.fullNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        registerButton = findViewById(R.id.registerButton);
        loginLink = findViewById(R.id.loginLink);

        registerButton.setOnClickListener(this);
        loginLink.setOnClickListener(this);
    }

    private void registerUser() {
        String fullName = fullNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        // Validation
        if (TextUtils.isEmpty(fullName)) {
            fullNameEditText.setError("Full name is required");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required");
            return;
        }
        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters");
            return;
        }
        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            return;
        }

        // Register with Firebase
        registerButton.setEnabled(false);
        registerButton.setText("Registering...");

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(task -> {
                registerButton.setEnabled(true);
                registerButton.setText("Register");

                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user != null) {
                        callRegisterApi(user.getUid(), fullName, email);
                    }
                } else {
                    Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
    }

    private void callRegisterApi(String firebaseUid, String fullName, String email) {
        String url = Factory.getHostApi() +  "/api/users/register";

        HttpRequest httpRequest = new HttpRequest(this);

        // Set headers
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        // Build JSON body
        JSONObject body = new JSONObject();
        try {
            body.put("firebase_uid", firebaseUid);
            body.put("full_name", fullName);
            body.put("email", email);
            body.put("phone_number", "0912345678");
            body.put("gender", "male");
            body.put("date_of_birth", "1995-06-15");
            body.put("address", "123 Main Street");
            body.put("role", "user");
        } catch (Exception e) {
            Toast.makeText(this, "Error creating request body", Toast.LENGTH_SHORT).show();
            return;
        }

        // Call API
        httpRequest.executeJsonRequest("POST", url, headers, body, new HttpRequest.JsonRequestCallback() {
            @Override
            public void onResponse(int statusCode, JSONObject response) {
                // Success
                Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_SHORT).show();
                nextActivityNoBack(LoginActivity.class);
            }

            @Override
            public void onErrorResponse(int statusCode, String response, VolleyError error) {
                Toast.makeText(RegistrationActivity.this, "API error: " + response, Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.loginLink) {
            nextActivity(LoginActivity.class);
        }
        else if(id == R.id.registerButton) {
            registerUser();
        }
    }
}
