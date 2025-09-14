package com.petcare.controller;

import com.petcare.model.Vaccination;
import com.petcare.service.VaccinationService;
import com.petcare.dto.VaccinationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/vaccinations")
@CrossOrigin(origins = "*", maxAge = 3600)
public class VaccinationController {

    @Autowired
    private VaccinationService vaccinationService;

    // Create new vaccination record (Only vets can create vaccination records)
    @PostMapping
    @PreAuthorize("hasRole('VET')")
    public ResponseEntity<?> createVaccination(@Valid @RequestBody VaccinationDTO vaccinationDTO,
                                             Authentication authentication) {
        try {
            Vaccination vaccination = vaccinationService.createVaccination(vaccinationDTO, authentication.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(vaccination);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating vaccination record: " + e.getMessage());
        }
    }

    // Get all vaccination records
    @GetMapping
    @PreAuthorize("hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<List<Vaccination>> getAllVaccinations() {
        List<Vaccination> vaccinations = vaccinationService.getAllVaccinations();
        return ResponseEntity.ok(vaccinations);
    }

    // Get vaccination by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') or hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<?> getVaccinationById(@PathVariable Long id,
                                              Authentication authentication) {
        try {
            Vaccination vaccination = vaccinationService.getVaccinationById(id);
            
            // Check if owner is accessing their own pet's vaccination record
            if (authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_OWNER"))) {
                if (!vaccinationService.isVaccinationOwnedByUser(id, authentication.getName())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Access denied: You can only view your own pet's vaccination records");
                }
            }
            
            return ResponseEntity.ok(vaccination);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get vaccinations by pet ID
    @GetMapping("/pet/{petId}")
    @PreAuthorize("hasRole('OWNER') or hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<?> getVaccinationsByPet(@PathVariable Long petId,
                                                Authentication authentication) {
        try {
            // Check if owner is accessing their own pet's vaccination records
            if (authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_OWNER"))) {
                if (!vaccinationService.isPetOwnedByUser(petId, authentication.getName())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Access denied: You can only view your own pet's vaccination records");
                }
            }
            
            List<Vaccination> vaccinations = vaccinationService.getVaccinationsByPetId(petId);
            return ResponseEntity.ok(vaccinations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching vaccination records: " + e.getMessage());
        }
    }

    // Get vaccinations by veterinarian
    @GetMapping("/vet/{vetId}")
    @PreAuthorize("hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<List<Vaccination>> getVaccinationsByVet(@PathVariable Long vetId) {
        List<Vaccination> vaccinations = vaccinationService.getVaccinationsByVetId(vetId);
        return ResponseEntity.ok(vaccinations);
    }

    // Get vaccinations by vaccine type
    @GetMapping("/vaccine-type/{vaccineType}")
    @PreAuthorize("hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<List<Vaccination>> getVaccinationsByVaccineType(@PathVariable String vaccineType) {
        List<Vaccination> vaccinations = vaccinationService.getVaccinationsByVaccineType(vaccineType);
        return ResponseEntity.ok(vaccinations);
    }

    // Get vaccinations by date range
    @GetMapping("/date-range")
    @PreAuthorize("hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<List<Vaccination>> getVaccinationsByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<Vaccination> vaccinations = vaccinationService.getVaccinationsByDateRange(startDate, endDate);
        return ResponseEntity.ok(vaccinations);
    }

    // Get upcoming vaccinations (due within next 30 days)
    @GetMapping("/upcoming")
    @PreAuthorize("hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<List<Vaccination>> getUpcomingVaccinations() {
        List<Vaccination> vaccinations = vaccinationService.getUpcomingVaccinations();
        return ResponseEntity.ok(vaccinations);
    }

    // Get overdue vaccinations
    @GetMapping("/overdue")
    @PreAuthorize("hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<List<Vaccination>> getOverdueVaccinations() {
        List<Vaccination> vaccinations = vaccinationService.getOverdueVaccinations();
        return ResponseEntity.ok(vaccinations);
    }

    // Get vaccination history for a specific pet
    @GetMapping("/pet/{petId}/history")
    @PreAuthorize("hasRole('OWNER') or hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<?> getVaccinationHistory(@PathVariable Long petId,
                                                 Authentication authentication) {
        try {
            // Check if owner is accessing their own pet's vaccination history
            if (authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_OWNER"))) {
                if (!vaccinationService.isPetOwnedByUser(petId, authentication.getName())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Access denied: You can only view your own pet's vaccination history");
                }
            }
            
            List<Vaccination> history = vaccinationService.getVaccinationHistory(petId);
            return ResponseEntity.ok(history);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching vaccination history: " + e.getMessage());
        }
    }

    // Update vaccination record
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('VET')")
    public ResponseEntity<?> updateVaccination(@PathVariable Long id,
                                             @Valid @RequestBody VaccinationDTO vaccinationDTO) {
        try {
            Vaccination updatedVaccination = vaccinationService.updateVaccination(id, vaccinationDTO);
            return ResponseEntity.ok(updatedVaccination);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating vaccination record: " + e.getMessage());
        }
    }

    // Schedule next vaccination
    @PostMapping("/{id}/schedule-next")
    @PreAuthorize("hasRole('VET')")
    public ResponseEntity<?> scheduleNextVaccination(@PathVariable Long id,
                                                   @RequestBody NextVaccinationDTO nextVaccinationDTO) {
        try {
            Vaccination nextVaccination = vaccinationService.scheduleNextVaccination(id, nextVaccinationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nextVaccination);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error scheduling next vaccination: " + e.getMessage());
        }
    }

    // Mark vaccination as completed
    @PutMapping("/{id}/complete")
    @PreAuthorize("hasRole('VET')")
    public ResponseEntity<?> completeVaccination(@PathVariable Long id,
                                               @RequestBody VaccinationCompletionDTO completionDTO) {
        try {
            Vaccination completedVaccination = vaccinationService.completeVaccination(id, completionDTO);
            return ResponseEntity.ok(completedVaccination);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error completing vaccination: " + e.getMessage());
        }
    }

    // Delete vaccination record
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteVaccination(@PathVariable Long id) {
        try {
            vaccinationService.deleteVaccination(id);
            return ResponseEntity.ok().body("Vaccination record deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting vaccination record: " + e.getMessage());
        }
    }

    // Get vaccination statistics
    @GetMapping("/stats")
    @PreAuthorize("hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<?> getVaccinationStatistics() {
        try {
            return ResponseEntity.ok(vaccinationService.getVaccinationStatistics());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching vaccination statistics");
        }
    }

    // Get vaccination reminders for a specific owner
    @GetMapping("/reminders/my-pets")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> getMyVaccinationReminders(Authentication authentication) {
        try {
            List<Vaccination> reminders = vaccinationService.getVaccinationRemindersByOwner(authentication.getName());
            return ResponseEntity.ok(reminders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching vaccination reminders: " + e.getMessage());
        }
    }

    // Get vaccination certificate
    @GetMapping("/{id}/certificate")
    @PreAuthorize("hasRole('OWNER') or hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<?> getVaccinationCertificate(@PathVariable Long id,
                                                      Authentication authentication) {
        try {
            // Check if owner is accessing their own pet's vaccination certificate
            if (authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_OWNER"))) {
                if (!vaccinationService.isVaccinationOwnedByUser(id, authentication.getName())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Access denied: You can only view your own pet's vaccination certificates");
                }
            }
            
            // This would generate a vaccination certificate (PDF/document)
            return ResponseEntity.ok(vaccinationService.generateVaccinationCertificate(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error generating vaccination certificate: " + e.getMessage());
        }
    }

    // Inner classes for request bodies
    public static class NextVaccinationDTO {
        private LocalDate scheduledDate;
        private String vaccineType;
        private String notes;

        // Getters and setters
        public LocalDate getScheduledDate() { return scheduledDate; }
        public void setScheduledDate(LocalDate scheduledDate) { this.scheduledDate = scheduledDate; }
        public String getVaccineType() { return vaccineType; }
        public void setVaccineType(String vaccineType) { this.vaccineType = vaccineType; }
        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }

    public static class VaccinationCompletionDTO {
        private LocalDate administeredDate;
        private String batchNumber;
        private String manufacturer;
        private LocalDate expiryDate;
        private String sideEffects;
        private String notes;

        // Getters and setters
        public LocalDate getAdministeredDate() { return administeredDate; }
        public void setAdministeredDate(LocalDate administeredDate) { this.administeredDate = administeredDate; }
        public String getBatchNumber() { return batchNumber; }
        public void setBatchNumber(String batchNumber) { this.batchNumber = batchNumber; }
        public String getManufacturer() { return manufacturer; }
        public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
        public LocalDate getExpiryDate() { return expiryDate; }
        public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
        public String getSideEffects() { return sideEffects; }
        public void setSideEffects(String sideEffects) { this.sideEffects = sideEffects; }
        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }
}