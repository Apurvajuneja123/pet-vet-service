package com.petcare.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import java.time.LocalDateTime;

public class AppointmentDTO {
    
    @NotNull(message = "Pet ID is required")
    private Long petId;
    
    @NotNull(message = "Vet ID is required")
    private Long vetId;
    
    @NotNull(message = "Appointment date and time is required")
    @Future(message = "Appointment date must be in the future")
    private LocalDateTime appointmentDateTime;
    
    @NotBlank(message = "Reason for visit is required")
    private String reasonForVisit;
    
    private String notes;
    
    private String status = "SCHEDULED"; // SCHEDULED, CONFIRMED, IN_PROGRESS, COMPLETED, CANCELLED
    
    private String priority = "NORMAL"; // LOW, NORMAL, HIGH, URGENT
    
    private Integer durationMinutes = 30; // Default 30 minutes
    
    // Default constructor
    public AppointmentDTO() {}
    
    // Constructor
    public AppointmentDTO(Long petId, Long vetId, LocalDateTime appointmentDateTime, String reasonForVisit) {
        this.petId = petId;
        this.vetId = vetId;
        this.appointmentDateTime = appointmentDateTime;
        this.reasonForVisit = reasonForVisit;
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
    
    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }
    
    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }
    
    public String getReasonForVisit() {
        return reasonForVisit;
    }
    
    public void setReasonForVisit(String reasonForVisit) {
        this.reasonForVisit = reasonForVisit;
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
    
    public String getPriority() {
        return priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    public Integer getDurationMinutes() {
        return durationMinutes;
    }
    
    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
}
