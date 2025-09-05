package com.example.medicalbooking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.medicalbooking.R;
import com.example.medicalbooking.factory.Factory;
import com.example.medicalbooking.utils.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class TreatmentDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView backArrow;
    private TextView diagnosisText, treatmentText, prescriptionText, notesText;

    private int historyId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_detail);

        initUI();
    }

    private void initUI() {
        backArrow = findViewById(R.id.backArrow);
        diagnosisText = findViewById(R.id.diagnosisText);
        treatmentText = findViewById(R.id.treatmentText);
        prescriptionText = findViewById(R.id.prescriptionText);
        notesText = findViewById(R.id.notesText);

        backArrow.setOnClickListener(this);

        Intent intent = getIntent();
        historyId = intent.getIntExtra("history_id", -1);

        if (historyId == -1) {
            Toast.makeText(this, "Invalid treatment!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        loadTreatment();

    }

    private void loadTreatment() {
        HttpRequest httpRequest = new HttpRequest(this);

        // Set headers
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        httpRequest.executeJsonRequest(
                "GET",
                Factory.getHostApi() + "/api/treatment_histories/" + historyId,
                headers,
                null,
                new HttpRequest.JsonRequestCallback() {
                    @Override
                    public void onResponse(int statusCode, JSONObject response) {
                        try {
                            if (response.has("data")) {
                                JSONObject treatment = response.getJSONObject("data");

                                // Prefill fields
                                diagnosisText.setText(treatment.optString("diagnosis", ""));
                                treatmentText.setText(treatment.optString("treatment", ""));
                                prescriptionText.setText(treatment.optString("prescription", ""));
                                notesText.setText(treatment.optString("notes", ""));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onErrorResponse(int statusCode, String response, VolleyError error) {
                        // No treatment exists yet → leave empty fields
                    }
                }
        );
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.backArrow) {
            this.finish();
        }
    }
}
