package com.example.medicalbooking.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.example.medicalbooking.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        checkLoggedIn();
    }

    private void checkLoggedIn() {
        try {
            MasterKey masterKey = new MasterKey.Builder(this)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();
            SharedPreferences prefs = EncryptedSharedPreferences.create(
                    this, "medicalbooking", masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
            String email = prefs.getString("email", "");
            String password = prefs.getString("password", "");

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        nextActivityNoBack(HomeActivity.class);
                    }
                    else {
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                nextActivity(LoginActivity.class);
                            }
                        }, 2000);
                    }
                }
            );
        }
        catch (Exception e) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    nextActivity(LoginActivity.class);
                }
            }, 2000);
        }
    }
}