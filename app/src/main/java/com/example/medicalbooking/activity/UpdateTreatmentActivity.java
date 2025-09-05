package com.example.medicalbooking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medicalbooking.R;
import com.android.volley.VolleyError;
import com.example.medicalbooking.factory.Factory;
import com.example.medicalbooking.utils.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UpdateTreatmentActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView backArrow;
    private EditText diagnosisEditText, treatmentEditText, prescriptionEditText, notesEditText;
    private Button saveButton;
    private ProgressBar progressBar;

    private int appointmentId;

    private boolean isEditMode = false;
    private int existingTreatmentId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_treatment);

        initUI();
    }

    private void initUI() {
        // Initialize views
        backArrow = findViewById(R.id.backArrow);
        diagnosisEditText = findViewById(R.id.diagnosisEditText);
        treatmentEditText = findViewById(R.id.treatmentEditText);
        prescriptionEditText = findViewById(R.id.prescriptionEditText);
        notesEditText = findViewById(R.id.notesEditText);
        saveButton = findViewById(R.id.saveTreatmentButton);
        progressBar = findViewById(R.id.progressBar);

        // Get appointment_id from Intent
        Intent intent = getIntent();
        appointmentId = intent.getIntExtra("appointment_id", -1);

        if (appointmentId == -1) {
            Toast.makeText(this, "Invalid appointment!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load existing treatment if available
        loadTreatment();

        backArrow.setOnClickListener(this);
        saveButton.setOnClickListener(this);
    }

    private void loadTreatment() {
        progressBar.setVisibility(View.VISIBLE);

        HttpRequest httpRequest = new HttpRequest(this);

        // Set headers
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        httpRequest.executeJsonRequest(
                "GET",
                Factory.getHostApi() + "/api/treatment_histories/appointment/" + appointmentId,
                headers,
                null,
                new HttpRequest.JsonRequestCallback() {
                    @Override
                    public void onResponse(int statusCode, JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            if (response.has("data")) {
                                JSONObject treatment = response.getJSONObject("data");

                                // Prefill fields
                                diagnosisEditText.setText(treatment.optString("diagnosis", ""));
                                treatmentEditText.setText(treatment.optString("treatment", ""));
                                prescriptionEditText.setText(treatment.optString("prescription", ""));
                                notesEditText.setText(treatment.optString("notes", ""));

                                existingTreatmentId = treatment.getInt("history_id");
                                isEditMode = true;
                                saveButton.setText("Update Treatment");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onErrorResponse(int statusCode, String response, VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        // No treatment exists yet → leave empty fields
                    }
                }
        );
    }

    private void saveTreatment() {
        String diagnosis = diagnosisEditText.getText().toString().trim();
        String treatment = treatmentEditText.getText().toString().trim();
        String prescription = prescriptionEditText.getText().toString().trim();
        String notes = notesEditText.getText().toString().trim();

        if (diagnosis.isEmpty() || treatment.isEmpty()) {
            Toast.makeText(this, "Diagnosis and Treatment are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        HttpRequest httpRequest = new HttpRequest(this);
        // header
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        // Prepare body
        JSONObject body = new JSONObject();
        try {
            body.put("appointment_id", appointmentId);
            body.put("diagnosis", diagnosis);
            body.put("treatment", treatment);
            body.put("prescription", prescription);
            body.put("notes", notes);
            if (isEditMode) {
                body.put("history_id", existingTreatmentId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        httpRequest.executeJsonRequest(
                "POST",
                Factory.getHostApi() + "/api/treatment_histories/" + (!isEditMode ? "add" : ("update/" + existingTreatmentId)),
                headers,
                body,
                new HttpRequest.JsonRequestCallback() {
                    @Override
                    public void onResponse(int statusCode, JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(UpdateTreatmentActivity.this,
                                isEditMode ? "Treatment updated!" : "Treatment added!",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onErrorResponse(int statusCode, String response, VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(UpdateTreatmentActivity.this,
                                "Failed to save treatment!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.saveTreatmentButton) {
            saveTreatment();
        }
        else if(id == R.id.backArrow) {
            this.finish();
        }
    }
}
