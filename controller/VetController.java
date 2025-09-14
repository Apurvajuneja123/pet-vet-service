package com.petcare.controller;

import com.petcare.model.Vet;
import com.petcare.service.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/vets")
@CrossOrigin(origins = "*", maxAge = 3600)
public class VetController {

    @Autowired
    private VetService vetService;

    // Create vet profile
    @PostMapping
    @PreAuthorize("hasRole('VET')")
    public ResponseEntity<?> createVet(@Valid @RequestBody Vet vet, Authentication authentication) {
        try {
            Vet createdVet = vetService.createVet(vet, authentication.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdVet);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating vet profile: " + e.getMessage());
        }
    }

    // Get all vets
    @GetMapping
    public ResponseEntity<List<Vet>> getAllVets() {
        List<Vet> vets = vetService.getAllVets();
        return ResponseEntity.ok(vets);
    }

    // Get vet by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getVetById(@PathVariable String id) {
        try {
            Vet vet = vetService.getVetById(id);
            return ResponseEntity.ok(vet);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get vet by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getVetByUserId(@PathVariable String userId) {
        try {
            Vet vet = vetService.getVetByUserId(userId);
            return ResponseEntity.ok(vet);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get vets by specialization
    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<List<Vet>> getVetsBySpecialization(@PathVariable String specialization) {
        List<Vet> vets = vetService.getVetsBySpecialization(specialization);
        return ResponseEntity.ok(vets);
    }

    // Get vets by clinic name
    @GetMapping("/clinic/{clinicName}")
    public ResponseEntity<List<Vet>> getVetsByClinicName(@PathVariable String clinicName) {
        List<Vet> vets = vetService.getVetsByClinicName(clinicName);
        return ResponseEntity.ok(vets);
    }

    // Search vets
    @GetMapping("/search")
    public ResponseEntity<List<Vet>> searchVets(@RequestParam String searchTerm) {
        List<Vet> vets = vetService.searchVets(searchTerm);
        return ResponseEntity.ok(vets);
    }

    // Update vet profile
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('VET')")
    public ResponseEntity<?> updateVet(@PathVariable String id, @Valid @RequestBody Vet vetDetails, Authentication authentication) {
        try {
            Vet updatedVet = vetService.updateVet(id, vetDetails);
            return ResponseEntity.ok(updatedVet);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating vet profile: " + e.getMessage());
        }
    }

    // Delete vet profile
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteVet(@PathVariable String id) {
        try {
            vetService.deleteVet(id);
            return ResponseEntity.ok().body("Vet profile deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting vet profile: " + e.getMessage());
        }
    }
}