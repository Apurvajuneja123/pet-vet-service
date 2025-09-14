package com.petcare.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "appointments")
public class Appointment {
    @Id
    private String id;
    
    private String petId;
    private String ownerId;
    private String vetId;
    
    private LocalDateTime appointmentDateTime;
    private int durationMinutes = 30;
    
    private String appointmentType;
    private String reason;
    private String notes;
    
    private AppointmentStatus status = AppointmentStatus.SCHEDULED;
    private Priority priority = Priority.NORMAL;
    
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public enum AppointmentStatus {
        SCHEDULED, CONFIRMED, IN_PROGRESS, COMPLETED, CANCELLED, NO_SHOW
    }

    public enum Priority {
        LOW, NORMAL, HIGH, URGENT
    }

    // Constructors
    public Appointment() {}

    public Appointment(String petId, String ownerId, String vetId, LocalDateTime appointmentDateTime, String reason) {
        this.petId = petId;
        this.ownerId = ownerId;
        this.vetId = vetId;
        this.appointmentDateTime = appointmentDateTime;
        this.reason = reason;
    }

    // All getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getPetId() { return petId; }
    public void setPetId(String petId) { this.petId = petId; }
    
    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }
    
    public String getVetId() { return vetId; }
    public void setVetId(String vetId) { this.vetId = vetId; }
    
    public LocalDateTime getAppointmentDateTime() { return appointmentDateTime; }
    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) { this.appointmentDateTime = appointmentDateTime; }
    
    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
    
    public String getAppointmentType() { return appointmentType; }
    public void setAppointmentType(String appointmentType) { this.appointmentType = appointmentType; }
    
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }
    
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}