package com.example.medicalbooking.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.example.medicalbooking.R;
import com.example.medicalbooking.adapter.AppointmentAdapter;
import com.example.medicalbooking.adapter.MedicationReminderAdapter;
import com.example.medicalbooking.factory.Factory;
import com.example.medicalbooking.model.Appointment;
import com.example.medicalbooking.model.MedicationReminder;
import com.example.medicalbooking.utils.HttpRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MedicationReminderActivity extends BaseActivity implements View.OnClickListener {

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

        loadMedicationReminders();

        addReminderButton.setOnClickListener(this);
        backArrow.setOnClickListener(this);
    }

    private void loadMedicationReminders() {
        HttpRequest httpRequest = new HttpRequest(this);

        // Set headers
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        reminderList = new ArrayList<>();

        httpRequest.executeJsonRequest("GET", Factory.getHostApi() + "/api/reminders/get/" + Factory.getCurrentUser().getUserId(),
                headers, new JSONObject(), new HttpRequest.JsonRequestCallback() {
            @Override
            public void onResponse(int statusCode, JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        MedicationReminder reminder = new MedicationReminder(
                                obj.getInt("reminder_id"),
                                obj.getInt("user_id"),
                                obj.getString("name"),
                                obj.getString("dosage"),
                                obj.getInt("times_per_day"),
                                obj.getString("reminder_time"),
                                obj.getString("start_date"),
                                obj.getString("end_date"),
                                obj.getString("status"),
                                obj.getString("notes"));
                        reminderList.add(reminder);
                    }

                    adapter = new MedicationReminderAdapter(reminderList, new MedicationReminderAdapter.OnReminderActionListener() {

                        @Override
                        public void onEditClicked(MedicationReminder reminder, int position) {
                            // go to edit screen
                        }

                        @Override
                        public void onDeleteClicked(MedicationReminder reminder, int position) {
                            // show deletion pop-up
                        }
                    });
                    reminderRecyclerView.setAdapter(adapter);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MedicationReminderActivity.this, "Parsing error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onErrorResponse(int statusCode, String response, VolleyError error) {
                Log.e("MedicationReminderActivity", "loadMedicationReminders error(" + statusCode + ": " + error.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.backArrow) {
            this.finish();
        }
        else if(id == R.id.addReminderButton) {
            nextActivity(AddMedicineReminderActivity.class);
        }
    }
}
