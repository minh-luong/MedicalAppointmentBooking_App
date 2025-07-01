package com.example.medicalbooking.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicalbooking.R;
import com.google.firebase.auth.FirebaseAuth;

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
                    Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
                    nextActivityNoBack(LoginActivity.class);
                } else {
                    Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
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
