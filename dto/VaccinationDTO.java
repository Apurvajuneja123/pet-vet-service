package com.petcare.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import java.time.LocalDate;

public class VaccinationDTO {
    
    @NotNull(message = "Pet ID is required")
    private Long petId;
    
    @NotNull(message = "Vet ID is required")
    private Long vetId;
    
    @NotBlank(message = "Vaccine type is required")
    private String vaccineType;
    
    @NotNull(message = "Scheduled date is required")
    @Future(message = "Scheduled date must be in the future")
    private LocalDate scheduledDate;
    
    private LocalDate administeredDate;
    
    private String batchNumber;
    
    private String manufacturer;
    
    private LocalDate expiryDate;
    
    private String sideEffects;
    
    private String notes;
    
    private String status = "SCHEDULED"; // SCHEDULED, COMPLETED, CANCELLED
    
    // Default constructor
    public VaccinationDTO() {}
    
    // Constructor
    public VaccinationDTO(Long petId, Long vetId, String vaccineType, LocalDate scheduledDate) {
        this.petId = petId;
        this.vetId = vetId;
        this.vaccineType = vaccineType;
        this.scheduledDate = scheduledDate;
    }
    
    // Getters and Setters
    public Long getPetId() {
        return petId;
    }
    
    public void setPetId(Long petId) {
        this.petId = petId;
    }
    
    public Long getVetId() {
        return vetId;
    }
    
    public void setVetId(Long vetId) {
        this.vetId = vetId;
    }
    
    public String getVaccineType() {
        return vaccineType;
    }
    
    public void setVaccineType(String vaccineType) {
        this.vaccineType = vaccineType;
    }
    
    public LocalDate getScheduledDate() {
        return scheduledDate;
    }
    
    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }
    
    public LocalDate getAdministeredDate() {
        return administeredDate;
    }
    
    public void setAdministeredDate(LocalDate administeredDate) {
        this.administeredDate = administeredDate;
    }
    
    public String getBatchNumber() {
        return batchNumber;
    }
    
    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }
    
    public String getManufacturer() {
        return manufacturer;
    }
    
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    
    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    public String getSideEffects() {
        return sideEffects;
    }
    
    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}
