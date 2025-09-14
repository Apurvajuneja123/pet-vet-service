package com.petcare.controller;

import com.petcare.model.MedicalHistory;
import com.petcare.service.MedicalHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/medical-history")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MedicalHistoryController {

    @Autowired
    private MedicalHistoryService medicalHistoryService;

    // Create medical history record
    @PostMapping
    @PreAuthorize("hasRole('VET')")
    public ResponseEntity<?> createMedicalHistory(@Valid @RequestBody MedicalHistory medicalHistory) {
        try {
            MedicalHistory createdRecord = medicalHistoryService.createMedicalHistory(medicalHistory);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRecord);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating medical history record: " + e.getMessage());
        }
    }

    // Get all medical history records
    @GetMapping
    @PreAuthorize("hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<List<MedicalHistory>> getAllMedicalHistories() {
        List<MedicalHistory> records = medicalHistoryService.getAllMedicalHistories();
        return ResponseEntity.ok(records);
    }

    // Get medical history by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') or hasRole('VET')")
    public ResponseEntity<?> getMedicalHistoryById(@PathVariable String id) {
        try {
            MedicalHistory record = medicalHistoryService.getMedicalHistoryById(id);
            return ResponseEntity.ok(record);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get medical history by pet ID
    @GetMapping("/pet/{petId}")
    @PreAuthorize("hasRole('OWNER') or hasRole('VET')")
    public ResponseEntity<List<MedicalHistory>> getMedicalHistoriesByPetId(@PathVariable String petId) {
        List<MedicalHistory> records = medicalHistoryService.getMedicalHistoriesByPetId(petId);
        return ResponseEntity.ok(records);
    }

    // Get medical history by vet ID
    @GetMapping("/vet/{vetId}")
    @PreAuthorize("hasRole('VET')")
    public ResponseEntity<List<MedicalHistory>> getMedicalHistoriesByVetId(@PathVariable String vetId) {
        List<MedicalHistory> records = medicalHistoryService.getMedicalHistoriesByVetId(vetId);
        return ResponseEntity.ok(records);
    }

    // Get recent medical history for a pet
    @GetMapping("/pet/{petId}/recent")
    @PreAuthorize("hasRole('OWNER') or hasRole('VET')")
    public ResponseEntity<List<MedicalHistory>> getRecentMedicalHistoryForPet(@PathVariable String petId) {
        List<MedicalHistory> records = medicalHistoryService.getRecentMedicalHistoryForPet(petId);
        return ResponseEntity.ok(records);
    }

    // Get medical history by date range
    @GetMapping("/date-range")
    @PreAuthorize("hasRole('OWNER') or hasRole('VET')")
    public ResponseEntity<List<MedicalHistory>> getMedicalHistoriesByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<MedicalHistory> records = medicalHistoryService.getMedicalHistoriesByDateRange(startDate, endDate);
        return ResponseEntity.ok(records);
    }

    // Get medical history by diagnosis
    @GetMapping("/diagnosis")
    @PreAuthorize("hasRole('VET')")
    public ResponseEntity<List<MedicalHistory>> getMedicalHistoriesByDiagnosis(@RequestParam String diagnosis) {
        List<MedicalHistory> records = medicalHistoryService.getMedicalHistoriesByDiagnosis(diagnosis);
        return ResponseEntity.ok(records);
    }

    // Update medical history record
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('VET')")
    public ResponseEntity<?> updateMedicalHistory(@PathVariable String id, @Valid @RequestBody MedicalHistory medicalHistoryDetails) {
        try {
            MedicalHistory updatedRecord = medicalHistoryService.updateMedicalHistory(id, medicalHistoryDetails);
            return ResponseEntity.ok(updatedRecord);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating medical history record: " + e.getMessage());
        }
    }

    // Delete medical history record
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteMedicalHistory(@PathVariable String id) {
        try {
            medicalHistoryService.deleteMedicalHistory(id);
            return ResponseEntity.ok().body("Medical history record deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting medical history record: " + e.getMessage());
        }
    }
}









