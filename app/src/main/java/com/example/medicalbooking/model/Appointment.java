package com.example.medicalbooking.model;

public class Appointment {
    private String date;
    private String time;
    private String doctor;
    private String clinic;

    public Appointment(String date, String time, String doctor, String clinic) {
        this.date = date;
        this.time = time;
        this.doctor = doctor;
        this.clinic = clinic;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getClinic() {
        return clinic;
    }
}

