package com.example.medicalbooking.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalbooking.R;
import com.example.medicalbooking.adapter.AppointmentAdapter;
import com.example.medicalbooking.model.Appointment;

import java.util.ArrayList;
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

        appointmentList = new ArrayList<>();
        appointmentList.add(new Appointment("May 25", "10:00 AM", "Dr. James Smith", "City Health Clinic"));
        appointmentList.add(new Appointment("May 27", "02:30 PM", "Dr. Olivia Lee", "Central Medical"));
        appointmentList.add(new Appointment("May 30", "11:15 AM", "Dr. Ahmed Rachel", "Sunrise Clinic"));

        adapter = new AppointmentAdapter(appointmentList, new AppointmentAdapter.OnAppointmentActionListener() {
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
        });

        appointmentsRecyclerView.setAdapter(adapter);
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
