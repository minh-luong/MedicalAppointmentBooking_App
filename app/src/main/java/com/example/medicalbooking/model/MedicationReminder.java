package com.example.medicalbooking.model;

public class MedicationReminder {
    private int reminderId;
    private int userId;
    private String name;
    private String dosage;
    private int timesPerDay;
    private String reminderTime;
    private String startDate;
    private String endDate;
    private String status;
    private String notes;

    public MedicationReminder(int reminderId, int userId, String name, String dosage,
                              int timesPerDay, String reminderTime,
                              String startDate, String endDate,
                              String status, String notes) {
        this.reminderId = reminderId;
        this.userId = userId;
        this.name = name;
        this.dosage = dosage;
        this.timesPerDay = timesPerDay;
        this.reminderTime = reminderTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.notes = notes;
    }

    public int getReminderId() {
        return reminderId;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public int getTimesPerDay() {
        return timesPerDay;
    }

    public void setTimesPerDay(int timesPerDay) {
        this.timesPerDay = timesPerDay;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
