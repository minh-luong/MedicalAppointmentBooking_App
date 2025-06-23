package com.example.medicalbooking.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalbooking.R;
import com.example.medicalbooking.model.MedicalHistory;

import java.util.List;

public class MedicalHistoryAdapter extends RecyclerView.Adapter<MedicalHistoryAdapter.ViewHolder> {

    private List<MedicalHistory> historyList;

    public MedicalHistoryAdapter(List<MedicalHistory> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medical_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MedicalHistory history = historyList.get(position);
        holder.dateText.setText(history.getDate());
        holder.diagnosisText.setText(history.getDiagnosis());
        holder.prescriptionText.setText(history.getPrescription());
        holder.notesText.setText(history.getNotes());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateText, diagnosisText, prescriptionText, notesText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.dateText);
            diagnosisText = itemView.findViewById(R.id.diagnosisText);
            prescriptionText = itemView.findViewById(R.id.prescriptionText);
            notesText = itemView.findViewById(R.id.notesText);
        }
    }
}
