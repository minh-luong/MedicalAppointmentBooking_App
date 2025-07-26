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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

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
        String formattedDate = appointment.getAppointmentTime().substring(0, 10);
        try {
            formattedDate = new SimpleDateFormat("yyyy MMM dd", Locale.getDefault())
                    .format(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(formattedDate));
        } catch (Exception e) {
            //
        }
        holder.dateText.setText(formattedDate);
        holder.timeText.setText(appointment.getAppointmentTime().substring(11, 19));
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


