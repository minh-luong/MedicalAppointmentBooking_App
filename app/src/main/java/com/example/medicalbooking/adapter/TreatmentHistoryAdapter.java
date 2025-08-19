package com.example.medicalbooking.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalbooking.R;
import com.example.medicalbooking.model.TreatmentHistory;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TreatmentHistoryAdapter extends RecyclerView.Adapter<TreatmentHistoryAdapter.ViewHolder> {

    private List<TreatmentHistory> historyList;

    public TreatmentHistoryAdapter(List<TreatmentHistory> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_treatment_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TreatmentHistory history = historyList.get(position);
        String formattedDate = history.getDate().substring(0, 10);
        try {
            formattedDate = new SimpleDateFormat("yyyy MMM dd", Locale.getDefault())
                    .format(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(formattedDate));
        }
        catch (Exception e) {
            //
        }
        holder.dateText.setText(formattedDate);
        holder.diagnosisText.setText(history.getDiagnosis());
        holder.doctorText.setText(history.getDoctor() + " (" + history.getSpecialty() + ")");
//        holder.treatmentText.setText(history.getTreatment());
//        holder.notesText.setText(history.getNotes());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateText, diagnosisText, doctorText; //treatmentText, notesText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.dateText);
            diagnosisText = itemView.findViewById(R.id.diagnosisText);
            doctorText = itemView.findViewById(R.id.doctorText);
//            treatmentText = itemView.findViewById(R.id.treatmentText);
//            notesText = itemView.findViewById(R.id.notesText);
        }
    }
}
