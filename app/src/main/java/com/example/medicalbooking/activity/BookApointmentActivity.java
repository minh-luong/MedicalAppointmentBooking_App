package com.example.medicalbooking.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicalbooking.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BookApointmentActivity extends BaseActivity implements View.OnClickListener {

    private Calendar selectedDateTime;

    private ImageView backArrow;
    private Spinner doctorSpinner;
    private LinearLayout dateTimePicker;
    private TextView dateTimeText;
    private EditText symptomsEditText;
    private Button confirmBookingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        initUI();
    }

    private void initUI() {
        backArrow = findViewById(R.id.backArrow);
        doctorSpinner = findViewById(R.id.doctorSpinner);
        dateTimePicker = findViewById(R.id.dateTimePicker);
        dateTimeText = findViewById(R.id.dateTimeText);
        symptomsEditText = findViewById(R.id.symptomsEditText);
        confirmBookingButton = findViewById(R.id.confirmBookingButton);

        backArrow.setOnClickListener(this);

        // Populate doctor spinner
        String[] doctors = {"Dr. Smith", "Dr. Johnson", "Dr. Lee", "Dr. Rachel"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, doctors);
        doctorSpinner.setAdapter(adapter);

        selectedDateTime = Calendar.getInstance();
        updateDateTimeText(); // Initial value

        dateTimePicker.setOnClickListener(this);

        confirmBookingButton.setOnClickListener(this);
    }

    private void showDateTimePicker() {
        // Show date picker first
        Calendar now = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            selectedDateTime.set(Calendar.YEAR, year);
            selectedDateTime.set(Calendar.MONTH, month);
            selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Show time picker after date is picked
            new TimePickerDialog(this, (timeView, hourOfDay, minute) -> {
                selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                selectedDateTime.set(Calendar.MINUTE, minute);
                updateDateTimeText();
            }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false).show();

        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateDateTimeText() {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy · hh:mm a", Locale.getDefault());
        dateTimeText.setText(formatter.format(selectedDateTime.getTime()));
    }

    private void confirmBooking() {
        String selectedDoctor = doctorSpinner.getSelectedItem().toString();
        String symptoms = symptomsEditText.getText().toString();
        String dateTime = dateTimeText.getText().toString();

        if (symptoms.trim().isEmpty()) {
            Toast.makeText(this, "Please enter your symptoms.", Toast.LENGTH_SHORT).show();
            return;
        }

        String message = "Appointment booked with " + selectedDoctor +
                "\nOn: " + dateTime +
                "\nSymptoms: " + symptoms;

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        this.finish();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.backArrow) {
            this.finish();
        }
        else if(id == R.id.dateTimePicker) {
            showDateTimePicker();
        }
        else if(id == R.id.confirmBookingButton) {
            confirmBooking();
        }
    }
}
