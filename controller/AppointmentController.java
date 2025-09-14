package com.petcare.controller;

import com.petcare.model.Appointment;
import com.petcare.service.AppointmentService;
import com.petcare.dto.AppointmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // Create new appointment (Owners can book appointments)
    @PostMapping
    @PreAuthorize("hasRole('OWNER') or hasRole('VET')")
    public ResponseEntity<?> createAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO) {
        try {
            Appointment appointment = appointmentService.createAppointment(appointmentDTO);
            return ResponseEntity.ok(appointment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating appointment: " + e.getMessage());
        }
    }

    // Get all appointments
    @GetMapping
    @PreAuthorize("hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    // Get appointment by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') or hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<?> getAppointmentById(@PathVariable Long id) {
        try {
            Appointment appointment = appointmentService.getAppointmentById(id);
            return ResponseEntity.ok(appointment);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get appointments by owner
    @GetMapping("/owner/{ownerId}")
    @PreAuthorize("hasRole('OWNER') or hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<List<Appointment>> getAppointmentsByOwner(@PathVariable Long ownerId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByOwnerId(ownerId);
        return ResponseEntity.ok(appointments);
    }

    // Get appointments by vet
    @GetMapping("/vet/{vetId}")
    @PreAuthorize("hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<List<Appointment>> getAppointmentsByVet(@PathVariable Long vetId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByVetId(vetId);
        return ResponseEntity.ok(appointments);
    }

    // Get appointments by pet
    @GetMapping("/pet/{petId}")
    @PreAuthorize("hasRole('OWNER') or hasRole('VET')")
    public ResponseEntity<List<Appointment>> getAppointmentsByPet(@PathVariable Long petId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByPetId(petId);
        return ResponseEntity.ok(appointments);
    }

    // Get appointments by date range
    @GetMapping("/date-range")
    @PreAuthorize("hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<List<Appointment>> getAppointmentsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDateRange(startDate, endDate);
        return ResponseEntity.ok(appointments);
    }

    // Get today's appointments
    @GetMapping("/today")
    @PreAuthorize("hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<List<Appointment>> getTodayAppointments() {
        List<Appointment> appointments = appointmentService.getTodayAppointments();
        return ResponseEntity.ok(appointments);
    }

    // Update appointment
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<?> updateAppointment(@PathVariable Long id, 
                                             @Valid @RequestBody AppointmentDTO appointmentDTO) {
        try {
            Appointment updatedAppointment = appointmentService.updateAppointment(id, appointmentDTO);
            return ResponseEntity.ok(updatedAppointment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating appointment: " + e.getMessage());
        }
    }

    // Cancel appointment
    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasRole('OWNER') or hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<?> cancelAppointment(@PathVariable Long id) {
        try {
            Appointment cancelledAppointment = appointmentService.cancelAppointment(id);
            return ResponseEntity.ok(cancelledAppointment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error cancelling appointment: " + e.getMessage());
        }
    }

    // Complete appointment
    @PutMapping("/{id}/complete")
    @PreAuthorize("hasRole('VET')")
    public ResponseEntity<?> completeAppointment(@PathVariable Long id, 
                                               @RequestBody String notes) {
        try {
            Appointment completedAppointment = appointmentService.completeAppointment(id, notes);
            return ResponseEntity.ok(completedAppointment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error completing appointment: " + e.getMessage());
        }
    }

    // Delete appointment
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        try {
            appointmentService.deleteAppointment(id);
            return ResponseEntity.ok().body("Appointment deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting appointment: " + e.getMessage());
        }
    }

    // Get appointment statistics
    @GetMapping("/stats")
    @PreAuthorize("hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<?> getAppointmentStats() {
        try {
            // This would return various statistics about appointments
            return ResponseEntity.ok(appointmentService.getAppointmentStatistics());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching appointment statistics");
        }
    }
}