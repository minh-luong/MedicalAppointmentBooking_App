package com.example.medicalbooking.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.example.medicalbooking.R;
import com.example.medicalbooking.adapter.TreatmentHistoryAdapter;
import com.example.medicalbooking.factory.Factory;
import com.example.medicalbooking.model.TreatmentHistory;
import com.example.medicalbooking.utils.HttpRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TreatmentHistoryActivity extends BaseActivity implements View.OnClickListener {

    private ImageView backArrow;
    private RecyclerView historyRecyclerView;
    private TreatmentHistoryAdapter adapter;
    private List<TreatmentHistory> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_history);

        initUI();
    }

    private void initUI() {
        backArrow = findViewById(R.id.backArrow);
        historyRecyclerView = findViewById(R.id.historyRecyclerView);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadTreatmentHistory();

        adapter = new TreatmentHistoryAdapter(historyList);
        historyRecyclerView.setAdapter(adapter);
        backArrow.setOnClickListener(this);
    }

    private void loadTreatmentHistory() {
        HttpRequest httpRequest = new HttpRequest(this);

        // Set headers
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        historyList = new ArrayList<>();

        httpRequest.executeJsonRequest("GET", Factory.getHostApi() + "/api/treatment_histories/get/" + Factory.getCurrentUser().getUserId(),
                headers, new JSONObject(), new HttpRequest.JsonRequestCallback() {
            @Override
            public void onResponse(int statusCode, JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("data");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);

                        TreatmentHistory history = new TreatmentHistory(
                            obj.getInt("history_id"),
                            obj.getInt("appointment_id"),
                            obj.getString("doctor_name"),
                            obj.getString("specialty_name"),
                            obj.getString("diagnosis"),
                            obj.getString("treatment"),
                            obj.getString("prescription"),
                            obj.getString("notes"),
                            obj.getString("updated_at")
                        );

                        historyList.add(history);
                    }

                    TreatmentHistoryAdapter adapter = new TreatmentHistoryAdapter(historyList);
                    historyRecyclerView.setAdapter(adapter);
                } catch (Exception e) {
                    Toast.makeText(TreatmentHistoryActivity.this, "Parse error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onErrorResponse(int statusCode, String response, VolleyError error) {
                Toast.makeText(TreatmentHistoryActivity.this, "Error: " + response, Toast.LENGTH_LONG).show();
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
