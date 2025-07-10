package com.example.medicalbooking.model;

public class Specialty {
    private int specialtyId;
    private String name;
    private String description;

    public Specialty() {
    }

    public Specialty(int specialtyId, String name, String description) {
        this.specialtyId = specialtyId;
        this.name = name;
        this.description = description;
    }

    // Getters
    public int getSpecialtyId() {
        return specialtyId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setSpecialtyId(int specialtyId) {
        this.specialtyId = specialtyId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Specialty{" +
                "specialtyId=" + specialtyId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
