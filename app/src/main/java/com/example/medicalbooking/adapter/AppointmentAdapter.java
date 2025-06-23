package com.example.medicalbooking.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalbooking.R;
import com.example.medicalbooking.model.Appointment;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {

    private List<Appointment> appointments;
    private OnAppointmentActionListener listener;

    public interface OnAppointmentActionListener {
        void onCancelClicked(Appointment appointment, int position);
        void onRescheduleClicked(Appointment appointment, int position);
    }

    public AppointmentAdapter(List<Appointment> appointments, OnAppointmentActionListener listener) {
        this.appointments = appointments;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.ViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);
        holder.dateText.setText(appointment.getDate());
        holder.timeText.setText(appointment.getTime());
        holder.doctorText.setText(appointment.getDoctor());
        holder.clinicText.setText(appointment.getClinic());

        holder.cancelButton.setOnClickListener(v ->
                listener.onCancelClicked(appointment, position));

        holder.rescheduleButton.setOnClickListener(v ->
                listener.onRescheduleClicked(appointment, position));
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateText, timeText, doctorText, clinicText;
        Button cancelButton, rescheduleButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.dateText);
            timeText = itemView.findViewById(R.id.timeText);
            doctorText = itemView.findViewById(R.id.doctorText);
            clinicText = itemView.findViewById(R.id.clinicText);
            cancelButton = itemView.findViewById(R.id.cancelButton);
            rescheduleButton = itemView.findViewById(R.id.rescheduleButton);
        }
    }
}


