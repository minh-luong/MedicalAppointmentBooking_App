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
import com.example.medicalbooking.model.MedicationReminder;

import java.util.List;

public class MedicationReminderAdapter extends RecyclerView.Adapter<MedicationReminderAdapter.ViewHolder> {

    private List<MedicationReminder> reminderList;

    public MedicationReminderAdapter(List<MedicationReminder> reminderList) {
        this.reminderList = reminderList;
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
        holder.medDosage.setText(reminder.getDosage());
        holder.medTime.setText(reminder.getTime());

        holder.editButton.setOnClickListener(v ->
                Toast.makeText(v.getContext(), "Edit: " + reminder.getName(), Toast.LENGTH_SHORT).show()
        );

        holder.deleteButton.setOnClickListener(v ->
                Toast.makeText(v.getContext(), "Delete: " + reminder.getName(), Toast.LENGTH_SHORT).show()
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
