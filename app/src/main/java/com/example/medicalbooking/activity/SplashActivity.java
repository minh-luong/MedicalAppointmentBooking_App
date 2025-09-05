package com.example.medicalbooking.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.android.volley.VolleyError;
import com.example.medicalbooking.R;
import com.example.medicalbooking.factory.Factory;
import com.example.medicalbooking.model.Doctor;
import com.example.medicalbooking.model.User;
import com.example.medicalbooking.utils.HttpRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

            FirebaseAuth.getInstance().getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        loadUserInformation(FirebaseAuth.getInstance().getCurrentUser().getUid());
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

    private void loadUserInformation(String uid) {
        HttpRequest httpRequest = new HttpRequest(this);

        // Set headers
        HashMap<String, String> headers = new HashMap<>();
        List<Doctor> doctors = new ArrayList<>();

        httpRequest.executeJsonRequest("GET", Factory.getHostApi() + "/api/users/get_info/" + uid, headers, new JSONObject(), new HttpRequest.JsonRequestCallback() {
            @Override
            public void onResponse(int statusCode, JSONObject response) {
                try {
                    JSONObject jsonObject = response.getJSONObject("data");
                    Factory.setCurrentUser(User.fromJson(jsonObject));
                    if(Factory.getCurrentUser().getRole().equals("doctor"))
                        nextActivityNoBack(DoctorHomeActivity.class);
                    else
                        nextActivityNoBack(HomeActivity.class);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(SplashActivity.this, "Parsing error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onErrorResponse(int statusCode, String response, VolleyError error) {
                //
            }
        });
    }
}