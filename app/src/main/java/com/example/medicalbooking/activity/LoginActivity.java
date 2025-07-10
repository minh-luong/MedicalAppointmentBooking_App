package com.example.medicalbooking.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import androidx.security.crypto.MasterKeys;

import com.example.medicalbooking.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView registerLink, forgotPasswordText;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        initUI();
    }

    private void initUI() {
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerLink = findViewById(R.id.registerLink);
        forgotPasswordText = findViewById(R.id.forgotPasswordText);

        registerLink.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        forgotPasswordText.setOnClickListener(this);
    }

    private void login() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required");
            return;
        }

        loginButton.setEnabled(false);
        loginButton.setText("Logging in...");

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(task -> {
                loginButton.setEnabled(true);
                loginButton.setText("Login");

                if (task.isSuccessful()) {
                    // Store to the Share Preference
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    try {
                        MasterKey masterKey = new MasterKey.Builder(this)
                                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                                .build();
                        SharedPreferences prefs = EncryptedSharedPreferences.create(
                                this, "medicalbooking", masterKey,
                                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("firebase_uid", currentUser.getUid());
                        editor.putString("email", email);
                        editor.putString("password", password);
                        editor.apply();
                    }
                    catch (Exception e) {
                        //
                    }

                    Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    nextActivityNoBack(HomeActivity.class);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
    }

    private void forgotPassword() {
        String email = emailEditText.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Enter your email first");
            return;
        }

        mAuth.sendPasswordResetEmail(email)
            .addOnSuccessListener(unused -> Toast.makeText(LoginActivity.this, "Reset link sent to email", Toast.LENGTH_SHORT).show())
            .addOnFailureListener(e -> Toast.makeText(LoginActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.registerLink) {
            nextActivity(RegistrationActivity.class);
        }
        else if(id == R.id.loginButton) {
            login();
//                nextActivity(HomeActivity.class);
        }
        else if(id == R.id.forgotPasswordText) {
            forgotPassword();
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

    }
}
