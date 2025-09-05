package com.example.medicalbooking.model;

public class Appointment {
    private int appointmentId;
    private String appointmentTime;
    private String doctor;
    private String clinic;
    private String patient;
    private String symptom;

    public Appointment(int appointmentId, String appointmentTime, String doctor, String clinic, String patient, String symptom) {
        this.appointmentId = appointmentId;
        this.appointmentTime = appointmentTime;
        this.doctor = doctor;
        this.clinic = clinic;
        this.patient = patient;
        this.symptom = symptom;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getClinic() {
        return clinic;
    }

    public String getPatient() {
        return patient;
    }

    public String getSymptom() {
        return symptom;
    }
}

