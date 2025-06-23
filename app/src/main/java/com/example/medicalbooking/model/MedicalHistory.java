package com.example.medicalbooking.model;

public class MedicalHistory {
    private String date;
    private String diagnosis;
    private String prescription;
    private String notes;

    public MedicalHistory(String date, String diagnosis, String prescription, String notes) {
        this.date = date;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public String getNotes() {
        return notes;
    }
}
