package com.petcare.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "vets")
public class Vet {
    @Id
    private String id;
    
    private String userId; // Reference to User
    private String licenseNumber;
    private String specialization;
    private int yearsOfExperience;
    private String clinicName;
    private String clinicAddress;
    private String clinicPhoneNumber;
    
    private List<String> availableDays;
    private String workingHours;
    
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Constructors, getters, and setters
    public Vet() {}

    public Vet(String userId, String licenseNumber, String specialization, String clinicName) {
        this.userId = userId;
        this.licenseNumber = licenseNumber;
        this.specialization = specialization;
        this.clinicName = clinicName;
    }

    // All getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
    
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    
    public int getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(int yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }
    
    public String getClinicName() { return clinicName; }
    public void setClinicName(String clinicName) { this.clinicName = clinicName; }
    
    public String getClinicAddress() { return clinicAddress; }
    public void setClinicAddress(String clinicAddress) { this.clinicAddress = clinicAddress; }
    
    public String getClinicPhoneNumber() { return clinicPhoneNumber; }
    public void setClinicPhoneNumber(String clinicPhoneNumber) { this.clinicPhoneNumber = clinicPhoneNumber; }
    
    public List<String> getAvailableDays() { return availableDays; }
    public void setAvailableDays(List<String> availableDays) { this.availableDays = availableDays; }
    
    public String getWorkingHours() { return workingHours; }
    public void setWorkingHours(String workingHours) { this.workingHours = workingHours; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}