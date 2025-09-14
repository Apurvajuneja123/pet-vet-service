package com.petcare.controller;

import com.petcare.model.Pet;
import com.petcare.service.PetService;
import com.petcare.dto.PetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/pets")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PetController {

    @Autowired
    private PetService petService;

    // Create new pet (Only owners can create pets)
    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> createPet(@Valid @RequestBody PetDTO petDTO, 
                                     Authentication authentication) {
        try {
            Pet pet = petService.createPet(petDTO, authentication.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(pet);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating pet: " + e.getMessage());
        }
    }

    // Get all pets (Vets and Admins can see all pets)
    @GetMapping
    @PreAuthorize("hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<List<Pet>> getAllPets() {
        List<Pet> pets = petService.getAllPets();
        return ResponseEntity.ok(pets);
    }

    // Get pet by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') or hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<?> getPetById(@PathVariable String id, 
                                      Authentication authentication) {
        try {
            Pet pet = petService.getPetById(id);
            
            // Check if owner is accessing their own pet
            if (authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_OWNER"))) {
                if (!petService.isPetOwnedByUser(id, authentication.getName())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Access denied: You can only view your own pets");
                }
            }
            
            return ResponseEntity.ok(pet);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get pets by owner
    @GetMapping("/owner/{ownerId}")
    @PreAuthorize("hasRole('OWNER') or hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<?> getPetsByOwner(@PathVariable String ownerId, 
                                          Authentication authentication) {
        try {
            // Check if owner is accessing their own pets
            if (authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_OWNER"))) {
                if (!petService.isOwnerUser(ownerId, authentication.getName())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Access denied: You can only view your own pets");
                }
            }
            
            List<Pet> pets = petService.getPetsByOwnerId(ownerId);
            return ResponseEntity.ok(pets);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching pets: " + e.getMessage());
        }
    }

    // Get current user's pets
    @GetMapping("/my-pets")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> getMyPets(Authentication authentication) {
        try {
            List<Pet> pets = petService.getPetsByUsername(authentication.getName());
            return ResponseEntity.ok(pets);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching your pets: " + e.getMessage());
        }
    }

    // Get pets by species
    @GetMapping("/species/{species}")
    @PreAuthorize("hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<List<Pet>> getPetsBySpecies(@PathVariable String species) {
        List<Pet> pets = petService.getPetsBySpecies(species);
        return ResponseEntity.ok(pets);
    }

    // Get pets by breed
    @GetMapping("/breed/{breed}")
    @PreAuthorize("hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<List<Pet>> getPetsByBreed(@PathVariable String breed) {
        List<Pet> pets = petService.getPetsByBreed(breed);
        return ResponseEntity.ok(pets);
    }

    // Get pets by age range
    @GetMapping("/age-range")
    @PreAuthorize("hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<List<Pet>> getPetsByAgeRange(@RequestParam int minAge, 
                                                     @RequestParam int maxAge) {
        List<Pet> pets = petService.getPetsByAgeRange(minAge, maxAge);
        return ResponseEntity.ok(pets);
    }

    // Search pets by name
    @GetMapping("/search")
    @PreAuthorize("hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<List<Pet>> searchPetsByName(@RequestParam String name) {
        List<Pet> pets = petService.searchPetsByName(name);
        return ResponseEntity.ok(pets);
    }

    // Update pet
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') or hasRole('VET')")
    public ResponseEntity<?> updatePet(@PathVariable String id, 
                                     @Valid @RequestBody PetDTO petDTO,
                                     Authentication authentication) {
        try {
            // Check if owner is updating their own pet
            if (authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_OWNER"))) {
                if (!petService.isPetOwnedByUser(id, authentication.getName())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Access denied: You can only update your own pets");
                }
            }
            
            Pet updatedPet = petService.updatePet(id, petDTO);
            return ResponseEntity.ok(updatedPet);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating pet: " + e.getMessage());
        }
    }

    // Update pet medical info (Only vets can update medical information)
    @PutMapping("/{id}/medical")
    @PreAuthorize("hasRole('VET')")
    public ResponseEntity<?> updatePetMedicalInfo(@PathVariable String id, 
                                                @RequestBody PetService.PetMedicalUpdateDTO medicalInfo) {
        try {
            Pet updatedPet = petService.updatePetMedicalInfo(id, medicalInfo);
            return ResponseEntity.ok(updatedPet);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating pet medical info: " + e.getMessage());
        }
    }

    // Delete pet
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN')")
    public ResponseEntity<?> deletePet(@PathVariable String id, 
                                     Authentication authentication) {
        try {
            // Check if owner is deleting their own pet
            if (authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_OWNER"))) {
                if (!petService.isPetOwnedByUser(id, authentication.getName())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Access denied: You can only delete your own pets");
                }
            }
            
            petService.deletePet(id);
            return ResponseEntity.ok().body("Pet deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting pet: " + e.getMessage());
        }
    }

    // Get pet statistics
    @GetMapping("/stats")
    @PreAuthorize("hasRole('VET') or hasRole('ADMIN')")
    public ResponseEntity<?> getPetStatistics() {
        try {
            return ResponseEntity.ok(petService.getPetStatistics());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching pet statistics");
        }
    }

    // Mark pet as adopted
    @PutMapping("/{id}/adopt")
    @PreAuthorize("hasRole('ADMIN') or hasRole('VET')")
    public ResponseEntity<?> markPetAsAdopted(@PathVariable String id, 
                                            @RequestBody PetService.AdoptionInfo adoptionInfo) {
        try {
            Pet adoptedPet = petService.markPetAsAdopted(id, adoptionInfo);
            return ResponseEntity.ok(adoptedPet);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error marking pet as adopted: " + e.getMessage());
        }
    }

}