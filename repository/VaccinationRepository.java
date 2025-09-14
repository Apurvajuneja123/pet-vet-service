package com.petcare.repository;

import com.petcare.model.Vaccination;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VaccinationRepository extends MongoRepository<Vaccination, String> {
    
    // Find vaccinations by pet ID
    List<Vaccination> findByPetId(String petId);
    
    // Find vaccinations by vet ID
    List<Vaccination> findByVetId(String vetId);
    
    // Find vaccinations by status
    List<Vaccination> findByStatus(Vaccination.VaccinationStatus status);
    
    // Find vaccinations by vaccine name
    List<Vaccination> findByVaccineNameIgnoreCase(String vaccineName);
    
    // Find vaccinations by vaccine type
    List<Vaccination> findByVaccineTypeIgnoreCase(String vaccineType);
    
    // Find vaccinations by date range
    @Query("{'administeredDate': {'$gte': ?0, '$lte': ?1}}")
    List<Vaccination> findByAdministeredDateBetween(LocalDate startDate, LocalDate endDate);
    
    // Find vaccinations by scheduled date range
    @Query("{'scheduledDate': {'$gte': ?0, '$lte': ?1}}")
    List<Vaccination> findByScheduledDateBetween(LocalDate startDate, LocalDate endDate);
    
    // Find upcoming vaccinations
    @Query("{'nextDueDate': {'$gte': ?0, '$lte': ?1}}")
    List<Vaccination> findUpcomingVaccinations(LocalDate startDate, LocalDate endDate);
    
    // Find overdue vaccinations
    @Query("{'nextDueDate': {'$lt': ?0}, 'status': 'SCHEDULED'}")
    List<Vaccination> findOverdueVaccinations(LocalDate currentDate);
    
    // Find vaccinations by pet and vaccine name
    List<Vaccination> findByPetIdAndVaccineNameIgnoreCase(String petId, String vaccineName);
    
    // Find vaccinations by pet ID ordered by scheduled date descending
    List<Vaccination> findByPetIdOrderByScheduledDateDesc(String petId);
    
    // Count vaccinations by status
    long countByStatus(Vaccination.VaccinationStatus status);
}