package com.petcare.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "vaccinations")
public class Vaccination {
    @Id
    private String id;
    
    private String petId;
    private String vetId;
    
    private String vaccineName;
    private String vaccineType;
    private String manufacturer;
    private String batchNumber;
    
    private LocalDate scheduledDate;
    private LocalDate administeredDate;
    private LocalDate nextDueDate;
    private LocalDate expiryDate;
    private String dosage;
    private String administrationMethod;
    
    private String notes;
    private String sideEffects;
    
    // Lifecycle states
    private VaccinationStatus status = VaccinationStatus.SCHEDULED;
    
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Enums for lifecycle
    public enum VaccinationStatus {
        SCHEDULED, ADMINISTERED, COMPLETED, OVERDUE, CANCELLED
    }

    // Constructors
    public Vaccination() {}

    public Vaccination(String petId, String vetId, String vaccineName, LocalDate administeredDate) {
        this.petId = petId;
        this.vetId = vetId;
        this.vaccineName = vaccineName;
        this.administeredDate = administeredDate;
    }

    // Lifecycle methods
    public void scheduleVaccination() {
        this.status = VaccinationStatus.SCHEDULED;
        this.updatedAt = LocalDateTime.now();
    }

    public void administerVaccination() {
        this.status = VaccinationStatus.ADMINISTERED;
        this.administeredDate = LocalDate.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void markOverdue() {
        this.status = VaccinationStatus.OVERDUE;
        this.updatedAt = LocalDateTime.now();
    }

    public void cancelVaccination() {
        this.status = VaccinationStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }

    // All getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getPetId() { return petId; }
    public void setPetId(String petId) { this.petId = petId; }
    
    public String getVetId() { return vetId; }
    public void setVetId(String vetId) { this.vetId = vetId; }
    
    public String getVaccineName() { return vaccineName; }
    public void setVaccineName(String vaccineName) { this.vaccineName = vaccineName; }
    
    public String getVaccineType() { return vaccineType; }
    public void setVaccineType(String vaccineType) { this.vaccineType = vaccineType; }
    
    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    
    public String getBatchNumber() { return batchNumber; }
    public void setBatchNumber(String batchNumber) { this.batchNumber = batchNumber; }
    
    public LocalDate getScheduledDate() { return scheduledDate; }
    public void setScheduledDate(LocalDate scheduledDate) { this.scheduledDate = scheduledDate; }
    
    public LocalDate getAdministeredDate() { return administeredDate; }
    public void setAdministeredDate(LocalDate administeredDate) { this.administeredDate = administeredDate; }
    
    public LocalDate getNextDueDate() { return nextDueDate; }
    public void setNextDueDate(LocalDate nextDueDate) { this.nextDueDate = nextDueDate; }
    
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    
    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }
    
    public String getAdministrationMethod() { return administrationMethod; }
    public void setAdministrationMethod(String administrationMethod) { this.administrationMethod = administrationMethod; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public String getSideEffects() { return sideEffects; }
    public void setSideEffects(String sideEffects) { this.sideEffects = sideEffects; }
    
    public VaccinationStatus getStatus() { return status; }
    public void setStatus(VaccinationStatus status) { this.status = status; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}