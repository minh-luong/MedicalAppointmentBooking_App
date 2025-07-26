package com.example.medicalbooking.model;

import androidx.annotation.NonNull;

public class Doctor {

    private int doctorId;
    private int specialtyId;
    private int clinicId;
    private String fullname;
    private String email;
    private String mobile;

    public Doctor(int doctorId, int specialtyId, int clinicId, String fullname) {
        this.doctorId = doctorId;
        this.specialtyId = specialtyId;
        this.clinicId = clinicId;
        this.fullname = fullname;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(int specialtyId) {
        this.specialtyId = specialtyId;
    }

    public int getClinicId() {
        return clinicId;
    }

    public void setClinicId(int clinicId) {
        this.clinicId = clinicId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return fullname;
    }
}
