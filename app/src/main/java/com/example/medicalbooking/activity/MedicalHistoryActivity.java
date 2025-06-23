package com.example.medicalbooking.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalbooking.R;
import com.example.medicalbooking.adapter.MedicalHistoryAdapter;
import com.example.medicalbooking.model.MedicalHistory;

import java.util.ArrayList;
import java.util.List;

public class MedicalHistoryActivity extends BaseActivity implements View.OnClickListener {

    private ImageView backArrow;
    private RecyclerView historyRecyclerView;
    private MedicalHistoryAdapter adapter;
    private List<MedicalHistory> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);

        initUI();
    }

    private void initUI() {
        backArrow = findViewById(R.id.backArrow);
        historyRecyclerView = findViewById(R.id.historyRecyclerView);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sample data
        historyList = new ArrayList<>();
        historyList.add(new MedicalHistory("Mar 15", "Hypertension", "Lisinopril 10 mg", "Monitor blood pressure regularly"));
        historyList.add(new MedicalHistory("Apr 05", "Diabetes Type 2", "Metformin 500 mg", "Reduce sugar intake, exercise"));
        historyList.add(new MedicalHistory("May 10", "Allergy", "Cetirizine", "Avoid pollen and dust"));

        adapter = new MedicalHistoryAdapter(historyList);
        historyRecyclerView.setAdapter(adapter);
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
