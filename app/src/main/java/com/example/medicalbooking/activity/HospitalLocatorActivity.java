package com.example.medicalbooking.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.medicalbooking.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HospitalLocatorActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText searchEditText;
    private Button specialtyFilterButton, distanceFilterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_locator);

        initUI();
    }

    private void initUI() {
        // Initialize views
        searchEditText = findViewById(R.id.searchEditText);
        specialtyFilterButton = findViewById(R.id.specialtyFilterButton);
        distanceFilterButton = findViewById(R.id.distanceFilterButton);

        // Setup Map Fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Handle search/filter interactions
        specialtyFilterButton.setOnClickListener(v -> {
            // TODO: Show specialty filter dialog or apply logic
        });

        distanceFilterButton.setOnClickListener(v -> {
            // TODO: Show distance filter dialog or apply logic
        });

        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            String query = v.getText().toString();
            // TODO: Perform search logic
            return true;
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Default location (e.g., city center)
        LatLng defaultLocation = new LatLng(10.762622, 106.660172); // Ho Chi Minh City
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12));

        // Sample hospital markers (replace with real data)
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(10.7758, 106.7004))
                .title("City Hospital"));

        mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(10.7625, 106.682)))
                .setTitle("HealthCare Clinic");
    }
}
