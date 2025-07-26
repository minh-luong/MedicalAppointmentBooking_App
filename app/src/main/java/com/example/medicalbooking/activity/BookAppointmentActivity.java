package com.example.medicalbooking.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.medicalbooking.R;
import com.example.medicalbooking.factory.Factory;
import com.example.medicalbooking.model.Doctor;
import com.example.medicalbooking.model.Specialty;
import com.example.medicalbooking.utils.HttpRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class BookAppointmentActivity extends BaseActivity implements View.OnClickListener {

    private Calendar selectedDateTime;

    private ImageView backArrow;
    private Spinner specialtySpinner, doctorSpinner;
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
        specialtySpinner = findViewById(R.id.departmentSpinner);
        doctorSpinner = findViewById(R.id.doctorSpinner);
        dateTimePicker = findViewById(R.id.dateTimePicker);
        dateTimeText = findViewById(R.id.dateTimeText);
        symptomsEditText = findViewById(R.id.symptomsEditText);
        confirmBookingButton = findViewById(R.id.confirmBookingButton);

        backArrow.setOnClickListener(this);

        // Specialties
        loadSpecialties();
        specialtySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Specialty selectedSpecialty = (Specialty) specialtySpinner.getSelectedItem();
                loadDoctors(selectedSpecialty.getSpecialtyId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        selectedDateTime = Calendar.getInstance();
        updateDateTimeText(); // Initial value

        dateTimePicker.setOnClickListener(this);

        confirmBookingButton.setOnClickListener(this);
    }

    private void loadSpecialties() {
        HttpRequest httpRequest = new HttpRequest(this);

        // Set headers
        HashMap<String, String> headers = new HashMap<>();

        List<Specialty> specialties = new ArrayList<>();
        specialties.add(new Specialty(-1, "Choose specialty", ""));

        httpRequest.executeJsonRequest("GET", Factory.getHostApi() + "/api/specialties", headers, new JSONObject(), new HttpRequest.JsonRequestCallback() {
            @Override
            public void onResponse(int statusCode, JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        Specialty s = new Specialty(obj.getInt("specialty_id"), obj.getString("name"), obj.getString("description"));
                        specialties.add(s);
                    }

                    ArrayAdapter<Specialty> departmentAdapter = new ArrayAdapter<Specialty>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, specialties);
                    specialtySpinner.setAdapter(departmentAdapter);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(BookAppointmentActivity.this, "Parsing error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onErrorResponse(int statusCode, String response, VolleyError error) {
                Toast.makeText(BookAppointmentActivity.this, "Failed to load specialties", Toast.LENGTH_SHORT).show();
                Log.e("BookAppointmentActivity",  "loadSpecialties error: " + error.toString());
            }
        });
    }

    private void loadDoctors(int specialtyId) {
        if(specialtyId == -1)
            return;

        HttpRequest httpRequest = new HttpRequest(this);

        // Set headers
        HashMap<String, String> headers = new HashMap<>();
        List<Doctor> doctors = new ArrayList<>();

        httpRequest.executeJsonRequest("GET", Factory.getHostApi() + "/api/doctors?specialty_id=" + specialtyId, headers, new JSONObject(), new HttpRequest.JsonRequestCallback() {
            @Override
            public void onResponse(int statusCode, JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        Doctor d = new Doctor(obj.getInt("doctor_id"), obj.getInt("specialty_id"), obj.getInt("clinic_id"), obj.getString("full_name"));
                        doctors.add(d);
                    }

                    ArrayAdapter<Doctor> doctorAdapter = new ArrayAdapter<Doctor>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, doctors);
                    doctorSpinner.setAdapter(doctorAdapter);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(BookAppointmentActivity.this, "Parsing error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onErrorResponse(int statusCode, String response, VolleyError error) {
                Toast.makeText(BookAppointmentActivity.this, "Failed to load doctors", Toast.LENGTH_SHORT).show();
                Log.e("BookAppointmentActivity",  "loadDoctors error: " + error.toString());
            }
        });
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
        Doctor selectedDoctor = (Doctor) doctorSpinner.getSelectedItem();
        Specialty selectedSpecialty = (Specialty) specialtySpinner.getSelectedItem();
        String symptoms = symptomsEditText.getText().toString();

        if (symptoms.trim().isEmpty()) {
            Toast.makeText(this, "Please enter your symptoms", Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat mysqlFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String appointmentTime = mysqlFormat.format(selectedDateTime.getTime());

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("user_id", Factory.getCurrentUser().getUserId());
            jsonBody.put("doctor_id", selectedDoctor.getDoctorId());
            jsonBody.put("appointment_time", appointmentTime);
            jsonBody.put("symptoms", symptoms);
            jsonBody.put("status", "Pending");
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }

        HttpRequest httpRequest = new HttpRequest(this);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        httpRequest.executeJsonRequest("POST", Factory.getHostApi() + "/api/appointments/create", headers, jsonBody, new HttpRequest.JsonRequestCallback() {
            @Override
            public void onResponse(int statusCode, JSONObject response) {
                Toast.makeText(BookAppointmentActivity.this, "Appointment booked successfully!", Toast.LENGTH_LONG).show();
                nextActivityNoBack(HomeActivity.class);
            }

            @Override
            public void onErrorResponse(int statusCode, String response, VolleyError error) {
                Toast.makeText(BookAppointmentActivity.this, "Booking failed: " + statusCode, Toast.LENGTH_SHORT).show();
                Log.e("APPOINTMENT_BOOK_ERROR", response);
            }
        });
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
