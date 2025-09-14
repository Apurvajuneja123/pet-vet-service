package com.petcare.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "medical_histories")
public class MedicalHistory {
    @Id
    private String id;
    
    private String petId;
    private String vetId;
    
    private LocalDate visitDate;
    private String visitReason;
    private String diagnosis;
    private String treatment;
    private String prescription;
    
    private List<String> symptoms;
    private String notes;
    
    private double weight;
    private double temperature;
    private String bloodPressure;
    private String heartRate;
    
    private List<String> attachments; // File paths/URLs
    
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Constructors
    public MedicalHistory() {}

    public MedicalHistory(String petId, String vetId, LocalDate visitDate, String diagnosis) {
        this.petId = petId;
        this.vetId = vetId;
        this.visitDate = visitDate;
        this.diagnosis = diagnosis;
    }

    // All getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getPetId() { return petId; }
    public void setPetId(String petId) { this.petId = petId; }
    
    public String getVetId() { return vetId; }
    public void setVetId(String vetId) { this.vetId = vetId; }
    
    public LocalDate getVisitDate() { return visitDate; }
    public void setVisitDate(LocalDate visitDate) { this.visitDate = visitDate; }
    
    public String getVisitReason() { return visitReason; }
    public void setVisitReason(String visitReason) { this.visitReason = visitReason; }
    
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    
    public String getTreatment() { return treatment; }
    public void setTreatment(String treatment) { this.treatment = treatment; }
    
    public String getPrescription() { return prescription; }
    public void setPrescription(String prescription) { this.prescription = prescription; }
    
    public List<String> getSymptoms() { return symptoms; }
    public void setSymptoms(List<String> symptoms) { this.symptoms = symptoms; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    
    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }
    
    public String getBloodPressure() { return bloodPressure; }
    public void setBloodPressure(String bloodPressure) { this.bloodPressure = bloodPressure; }
    
    public String getHeartRate() { return heartRate; }
    public void setHeartRate(String heartRate) { this.heartRate = heartRate; }
    
    public List<String> getAttachments() { return attachments; }
    public void setAttachments(List<String> attachments) { this.attachments = attachments; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}