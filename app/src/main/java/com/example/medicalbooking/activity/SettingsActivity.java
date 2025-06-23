package com.example.medicalbooking.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.medicalbooking.R;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initUI();
    }

    private void initUI() {
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.backArrow) {
            this.finish();
        }
    }
}
