package com.example.medicalbooking.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.medicalbooking.R;
import com.example.medicalbooking.factory.Factory;
import com.example.medicalbooking.utils.HttpRequest;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;

public class AddMedicineReminderActivity extends AppCompatActivity {

    private ImageView backArrow;
    private EditText nameInput, dosageInput, timesPerDayInput, notesInput;
    private Button addTimeButton, startDateButton, endDateButton, saveButton;
    private TextView dateSummary;
    private ChipGroup timeChipGroup;

    private final ArrayList<String> timeList = new ArrayList<>();
    private String startDateISO = null; // "YYYY-MM-DD"
    private String endDateISO = null;   // "YYYY-MM-DD"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine_reminder);

        bindViews();
        wireUi();
    }

    private void bindViews() {
        backArrow = findViewById(R.id.backArrow);
        nameInput = findViewById(R.id.nameInput);
        dosageInput = findViewById(R.id.dosageInput);
        timesPerDayInput = findViewById(R.id.timesPerDayInput);
        notesInput = findViewById(R.id.notesInput);
        addTimeButton = findViewById(R.id.addTimeButton);
        startDateButton = findViewById(R.id.startDateButton);
        endDateButton = findViewById(R.id.endDateButton);
        saveButton = findViewById(R.id.saveButton);
        timeChipGroup = findViewById(R.id.timeChipGroup);
        dateSummary = findViewById(R.id.dateSummary);
    }

    private void wireUi() {
        backArrow.setOnClickListener(v -> {
            this.finish();
        });
        addTimeButton.setOnClickListener(v -> showTimePicker());
        startDateButton.setOnClickListener(v -> showDatePicker(true));
        endDateButton.setOnClickListener(v -> showDatePicker(false));
        saveButton.setOnClickListener(v -> attemptSave());
    }

    private void showTimePicker() {
        Calendar now = Calendar.getInstance();
        int h = now.get(Calendar.HOUR_OF_DAY);
        int m = now.get(Calendar.MINUTE);

        new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            String hh = String.format("%02d", hourOfDay);
            String mm = String.format("%02d", minute);
            String time = hh + ":" + mm;     // store HH:mm
            if (!timeList.contains(time)) {
                timeList.add(time);
                Collections.sort(timeList);
                refreshTimeChips();
            } else {
                Toast.makeText(this, "Time already added", Toast.LENGTH_SHORT).show();
            }
        }, h, m, true).show();
    }

    private void refreshTimeChips() {
        timeChipGroup.removeAllViews();
        for (String t : timeList) {
            Chip chip = new Chip(this);
            chip.setText(t);
            chip.setCloseIconVisible(true);
            chip.setOnCloseIconClickListener(v -> {
                timeList.remove(t);
                refreshTimeChips();
            });
            timeChipGroup.addView(chip);
        }
    }

    private void showDatePicker(boolean isStart) {
        Calendar now = Calendar.getInstance();
        int y = now.get(Calendar.YEAR);
        int mo = now.get(Calendar.MONTH);
        int d = now.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dlg = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            String iso = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
            if (isStart) startDateISO = iso; else endDateISO = iso;
            updateDateSummary();
        }, y, mo, d);

        dlg.show();
    }

    private void updateDateSummary() {
        String s = (startDateISO == null) ? "—" : startDateISO;
        String e = (endDateISO == null) ? "—" : endDateISO;
        dateSummary.setText("From " + s + " to " + e);
    }

    private void attemptSave() {
        String name = nameInput.getText().toString().trim();
        String dosage = dosageInput.getText().toString().trim();
        String timesPerDayStr = timesPerDayInput.getText().toString().trim();
        String notes = notesInput.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            nameInput.setError("Required");
            return;
        }
        if (TextUtils.isEmpty(dosage)) {
            dosageInput.setError("Required");
            return;
        }
        if (TextUtils.isEmpty(timesPerDayStr)) {
            timesPerDayInput.setError("Required");
            return;
        }
        int timesPerDay = Integer.parseInt(timesPerDayStr);
        if (timesPerDay <= 0) {
            timesPerDayInput.setError("Must be > 0");
            return;
        }
        if (timeList.size() != timesPerDay) {
            Toast.makeText(this, "Times per day must equal number of times selected", Toast.LENGTH_LONG).show();
            return;
        }
        if (startDateISO == null || endDateISO == null) {
            Toast.makeText(this, "Please choose start and end date", Toast.LENGTH_SHORT).show();
            return;
        }

        // Build comma-separated string "HH:mm,HH:mm..."
        String reminderTime = TextUtils.join(",", timeList);

        postReminder(name, dosage, timesPerDay, reminderTime, startDateISO, endDateISO, notes);
    }

    private void postReminder(String name, String dosage, int timesPerDay, String reminderTime,
                              String startDate, String endDate, String notes) {

        Integer userId = Factory.getCurrentUser().getUserId();
        if (userId == 0) {
            Toast.makeText(this, "Missing user_id. Please log in again.", Toast.LENGTH_LONG).show();
            return;
        }

        String url = Factory.getHostApi() + "/api/reminders/create";
        HttpRequest http = new HttpRequest(this);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        JSONObject body = new JSONObject();
        try {
            body.put("user_id", userId);
            body.put("name", name);
            body.put("dosage", dosage);
            body.put("times_per_day", timesPerDay);
            body.put("reminder_time", reminderTime);   // e.g. "08:00,13:00,20:00"
            body.put("start_date", startDate);         // "YYYY-MM-DD"
            body.put("end_date", endDate);             // "YYYY-MM-DD"
            body.put("status", "active");
            body.put("notes", notes);
        } catch (Exception e) {
            Toast.makeText(this, "Failed to build request", Toast.LENGTH_SHORT).show();
            return;
        }

        saveButton.setEnabled(false);
        saveButton.setText("Saving...");

        http.executeJsonRequest("POST", url, headers, body, new HttpRequest.JsonRequestCallback() {
            @Override
            public void onResponse(int statusCode, JSONObject response) {
                saveButton.setEnabled(true);
                saveButton.setText("Save Reminder");
                Toast.makeText(AddMedicineReminderActivity.this, "Reminder created", Toast.LENGTH_SHORT).show();

                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onErrorResponse(int statusCode, String response, VolleyError error) {
                saveButton.setEnabled(true);
                saveButton.setText("Save Reminder");
                String msg = (response != null && !response.isEmpty()) ? response : "Network error";
                Toast.makeText(AddMedicineReminderActivity.this, "Create failed: " + msg, Toast.LENGTH_LONG).show();
            }
        });
    }
}
