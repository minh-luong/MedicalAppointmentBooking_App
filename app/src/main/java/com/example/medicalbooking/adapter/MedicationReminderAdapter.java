package com.example.medicalbooking.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalbooking.R;
import com.example.medicalbooking.model.Appointment;
import com.example.medicalbooking.model.MedicationReminder;

import java.util.List;

public class MedicationReminderAdapter extends RecyclerView.Adapter<MedicationReminderAdapter.ViewHolder> {

    private List<MedicationReminder> reminderList;
    private OnReminderActionListener listener;

    public interface OnReminderActionListener {
        void onEditClicked(MedicationReminder reminder, int position);
        void onDeleteClicked(MedicationReminder reminder, int position);
    }

    public MedicationReminderAdapter(List<MedicationReminder> reminderList, OnReminderActionListener listener) {
        this.reminderList = reminderList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MedicationReminderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medication_reminder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationReminderAdapter.ViewHolder holder, int position) {
        MedicationReminder reminder = reminderList.get(position);
        holder.medName.setText(reminder.getName());

        String timesPerDayStr = "";
        if(reminder.getTimesPerDay() == 1)
            timesPerDayStr = "Once ";
        else if(reminder.getTimesPerDay() == 2)
            timesPerDayStr = "Twice ";
        else
            timesPerDayStr = reminder.getTimesPerDay() + " times ";
        timesPerDayStr += "daily";
        holder.medDosage.setText(reminder.getDosage() + " - " + timesPerDayStr);
        holder.medTime.setText(reminder.getReminderTime());

        holder.editButton.setOnClickListener(v ->
            this.listener.onEditClicked(reminder, position)
        );

        holder.deleteButton.setOnClickListener(v ->
            this.listener.onDeleteClicked(reminder, position)
        );
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView medName, medDosage, medTime;
        Button editButton, deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            medName = itemView.findViewById(R.id.medName);
            medDosage = itemView.findViewById(R.id.medDosage);
            medTime = itemView.findViewById(R.id.medTime);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
