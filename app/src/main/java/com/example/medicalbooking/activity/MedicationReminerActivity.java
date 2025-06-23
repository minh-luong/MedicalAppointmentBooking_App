package com.example.medicalbooking.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalbooking.R;
import com.example.medicalbooking.adapter.MedicationReminderAdapter;
import com.example.medicalbooking.model.MedicationReminder;

import java.util.ArrayList;
import java.util.List;

public class MedicationReminerActivity extends BaseActivity implements View.OnClickListener {

    private ImageView backArrow;
    private RecyclerView reminderRecyclerView;
    private MedicationReminderAdapter adapter;
    private List<MedicationReminder> reminderList;
    private Button addReminderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_reminder);

        initUI();
    }

    private void initUI() {
        backArrow = findViewById(R.id.backArrow);
        reminderRecyclerView = findViewById(R.id.reminderRecyclerView);
        addReminderButton = findViewById(R.id.addReminderButton);

        reminderRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sample data
        reminderList = new ArrayList<>();
        reminderList.add(new MedicationReminder("Aspirin", "1 tablet - Once daily", "8:00 AM"));
        reminderList.add(new MedicationReminder("Metformin", "500 mg - Twice daily", "8:00 AM, 6:00 PM"));

        adapter = new MedicationReminderAdapter(reminderList);
        reminderRecyclerView.setAdapter(adapter);

        addReminderButton.setOnClickListener(this);
        backArrow.setOnClickListener(this);
    }

    void addReminder() {
        Toast.makeText(this, "Add Reminder Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.backArrow) {
            this.finish();
        }
        else if(id == R.id.addReminderButton) {
            addReminder();
        }
    }
}
