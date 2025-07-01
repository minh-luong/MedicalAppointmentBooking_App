package com.example.medicalbooking.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicalbooking.factory.Factory;

public class BaseActivity extends AppCompatActivity {

    private final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        Factory.setCurrentActivity(this);
        super.onStart();
    }

    @Override
    protected void onResume() {
        Factory.setCurrentActivity(this);
        super.onResume();
    }

    public void nextActivity(Class<?> _class) {
        startActivity(new Intent(getApplicationContext(), _class));
    }

    public void nextActivityNoBack(Class<?> _class) {
        startActivity(new Intent(getApplicationContext(), _class));
        finish();
    }

    public void nextActivityWithParam(Class<?> _class, Bundle bundle) {
        Intent intent = new Intent(getApplicationContext(), _class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void nextActivityWithParamNoBack(Class<?> _class, Bundle bundle) {
        Intent intent = new Intent(getApplicationContext(), _class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
