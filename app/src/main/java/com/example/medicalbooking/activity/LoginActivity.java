package com.example.medicalbooking.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.medicalbooking.R;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText emailEd, passwordEd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();
    }

    private void initUI() {
        emailEd = (EditText) findViewById(R.id.emailEditText);
        passwordEd = (EditText) findViewById(R.id.passwordEditText);

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
//            if(emailEd.getText().toString().equals("test@gmail.com") && passwordEd.getText().toString().equals("12345678"))
                nextActivity(HomeActivity.class);
        }
    }
}
