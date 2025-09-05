package com.example.medicalbooking.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.example.medicalbooking.R;
import com.example.medicalbooking.adapter.AppointmentAdapter;
import com.example.medicalbooking.factory.Factory;
import com.example.medicalbooking.model.Appointment;
import com.example.medicalbooking.model.Doctor;
import com.example.medicalbooking.utils.HttpRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UpcomingAppointmentsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView backArrow;
    private RecyclerView appointmentsRecyclerView;
    private AppointmentAdapter adapter;
    private List<Appointment> appointmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_appointments);

        initUI();
    }

    private void initUI() {
        backArrow = findViewById(R.id.backArrow);
        appointmentsRecyclerView = findViewById(R.id.appointmentsRecyclerView);
        appointmentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(Factory.getCurrentUser().getRole().equals("doctor"))
            loadUpcomingAppointmentForDoctor();
        else
            loadUpcomingAppointmentForUser();

        backArrow.setOnClickListener(this);
    }

    private void loadUpcomingAppointmentForUser() {
        HttpRequest httpRequest = new HttpRequest(this);

        // Set headers
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        appointmentList = new ArrayList<>();

        httpRequest.executeJsonRequest("GET", Factory.getHostApi() + "/api/appointments/user/" + Factory.getCurrentUser().getUserId(),
                headers, new JSONObject(), new HttpRequest.JsonRequestCallback() {
            @Override
            public void onResponse(int statusCode, JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        Appointment appointment = new Appointment(
                                obj.getInt("appointment_id"),
                                obj.getString("appointment_time"),
                                obj.getString("doctor_name"),
                                obj.getString("clinic_name"),
                                "",
                                obj.getString("symptoms"));
                        appointmentList.add(appointment);
                    }

                    adapter = new AppointmentAdapter(appointmentList, false, new AppointmentAdapter.OnAppointmentActionListener() {
                        @Override
                        public void onCancelClicked(Appointment appointment, int position) {
                            Toast.makeText(UpcomingAppointmentsActivity.this, "Cancelled: " + appointment.getDoctor(), Toast.LENGTH_SHORT).show();
                            // Optionally remove item
                            // appointmentList.remove(position);
                            // adapter.notifyItemRemoved(position);
                        }

                        @Override
                        public void onRescheduleClicked(Appointment appointment, int position) {
                            Toast.makeText(UpcomingAppointmentsActivity.this, "Reschedule: " + appointment.getDoctor(), Toast.LENGTH_SHORT).show();
                            // Implement rescheduling logic
                        }

                        @Override
                        public void onTreatmentUpdateClicked(Appointment appointment, int position) {

                        }
                    });
                    appointmentsRecyclerView.setAdapter(adapter);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(UpcomingAppointmentsActivity.this, "Parsing error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onErrorResponse(int statusCode, String response, VolleyError error) {
                Log.e("UpcomingAppointmentsActivity", "loadUpcomingAppointments error(" + statusCode + ": " + error.toString());
            }
        });
    }

    private void loadUpcomingAppointmentForDoctor() {
        HttpRequest httpRequest = new HttpRequest(this);

        // Set headers
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        appointmentList = new ArrayList<>();

        httpRequest.executeJsonRequest("GET", Factory.getHostApi() + "/api/appointments/doctor/" + Factory.getCurrentUser().getUserId(),
                headers, new JSONObject(), new HttpRequest.JsonRequestCallback() {
            @Override
            public void onResponse(int statusCode, JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        Appointment appointment = new Appointment(
                                obj.getInt("appointment_id"),
                                obj.getString("appointment_time"),
                                "",
                                "",
                                obj.getString("patient_name"),
                                obj.getString("symptoms"));
                        appointmentList.add(appointment);
                    }

                    adapter = new AppointmentAdapter(appointmentList, true, new AppointmentAdapter.OnAppointmentActionListener() {
                        @Override
                        public void onCancelClicked(Appointment appointment, int position) {

                        }

                        @Override
                        public void onRescheduleClicked(Appointment appointment, int position) {

                        }

                        @Override
                        public void onTreatmentUpdateClicked(Appointment appointment, int position) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("appointment_id", appointment.getAppointmentId());
                            nextActivityWithParam(UpdateTreatmentActivity.class, bundle);
                        }
                    });
                    appointmentsRecyclerView.setAdapter(adapter);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(UpcomingAppointmentsActivity.this, "Parsing error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onErrorResponse(int statusCode, String response, VolleyError error) {
                Log.e("UpcomingAppointmentsActivity", "loadUpcomingAppointments error(" + statusCode + ": " + error.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.backArrow) {
            this.finish();
        }
    }
}
