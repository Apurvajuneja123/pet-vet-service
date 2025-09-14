package com.petcare.repository;

import com.petcare.model.Vet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VetRepository extends MongoRepository<Vet, String> {
    
    // Find vet by user ID
    Optional<Vet> findByUserId(String userId);
    
    // Find vets by specialization
    List<Vet> findBySpecializationIgnoreCase(String specialization);
    
    // Find vets by clinic name
    List<Vet> findByClinicNameIgnoreCase(String clinicName);
    
    // Find vets by license number
    Optional<Vet> findByLicenseNumber(String licenseNumber);
    
    // Find vets by experience range
    @Query("{'yearsOfExperience': {'$gte': ?0, '$lte': ?1}}")
    List<Vet> findByYearsOfExperienceBetween(Integer minYears, Integer maxYears);
    
    // Search vets by name (through user relationship)
    @Query("{'$or': [{'clinicName': {'$regex': ?0, '$options': 'i'}}, {'specialization': {'$regex': ?0, '$options': 'i'}}]}")
    List<Vet> searchVets(String searchTerm);
    
    // Check if license number exists
    boolean existsByLicenseNumber(String licenseNumber);
}