package com.example.medicalbooking.model;

import org.json.JSONObject;

public class User {
    private int userId;
    private String firebaseUid;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String gender;
    private String dateOfBirth;
    private String address;
    private String role;
    private String createdAt;

    public User() {}

    public User(int userId, String firebaseUid, String fullName, String email, String phoneNumber,
                String gender, String dateOfBirth, String address, String role, String createdAt) {
        this.userId = userId;
        this.firebaseUid = firebaseUid;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.role = role;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirebaseUid() {
        return firebaseUid;
    }

    public void setFirebaseUid(String firebaseUid) {
        this.firebaseUid = firebaseUid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public static User fromJson(JSONObject obj) {
        User user = new User();
        user.setUserId(obj.optInt("user_id"));
        user.setFirebaseUid(obj.optString("firebase_uid"));
        user.setFullName(obj.optString("full_name"));
        user.setEmail(obj.optString("email"));
        user.setPhoneNumber(obj.optString("phone_number"));
        user.setGender(obj.optString("gender"));
        user.setDateOfBirth(obj.optString("date_of_birth"));
        user.setAddress(obj.optString("address"));
        user.setRole(obj.optString("role"));
        user.setCreatedAt(obj.optString("created_at"));
        return user;
    }
}