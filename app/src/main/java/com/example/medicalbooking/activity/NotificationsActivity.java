package com.example.medicalbooking.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalbooking.R;
import com.example.medicalbooking.adapter.NotificationAdapter;
import com.example.medicalbooking.model.NotificationItem;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView backArrow;
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<NotificationItem> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        initUI();
    }

    private void initUI() {
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(this);

        recyclerView = findViewById(R.id.notificationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sample notifications
        notificationList = new ArrayList<>();
        notificationList.add(new NotificationItem("Appointment Reminder", "You have an appointment tomorrow at 10:00 AM.", "2h ago"));
        notificationList.add(new NotificationItem("Medication Reminder", "Time to take Aspirin - 1 tablet", "5h ago"));
        notificationList.add(new NotificationItem("Doctor Message", "Dr. James has sent a note regarding your last visit.", "1d ago"));

        adapter = new NotificationAdapter(notificationList);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.backArrow) {
            this.finish();
        }
    }
}
