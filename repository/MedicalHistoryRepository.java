package com.petcare.repository;

import com.petcare.model.MedicalHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MedicalHistoryRepository extends MongoRepository<MedicalHistory, String> {
    
    // Find medical history by pet ID
    List<MedicalHistory> findByPetId(String petId);
    
    // Find medical history by vet ID
    List<MedicalHistory> findByVetId(String vetId);
    
    // Find medical history by date range
    @Query("{'visitDate': {'$gte': ?0, '$lte': ?1}}")
    List<MedicalHistory> findByVisitDateBetween(LocalDate startDate, LocalDate endDate);
    
    // Find medical history by pet and date range
    @Query("{'petId': ?0, 'visitDate': {'$gte': ?1, '$lte': ?2}}")
    List<MedicalHistory> findByPetIdAndVisitDateBetween(String petId, LocalDate startDate, LocalDate endDate);
    
    // Find medical history by diagnosis
    @Query("{'diagnosis': {'$regex': ?0, '$options': 'i'}}")
    List<MedicalHistory> findByDiagnosisContaining(String diagnosis);
    
    // Find medical history by visit reason
    @Query("{'visitReason': {'$regex': ?0, '$options': 'i'}}")
    List<MedicalHistory> findByVisitReasonContaining(String visitReason);
    
    // Find recent medical history for a pet
    @Query("{'petId': ?0}")
    List<MedicalHistory> findByPetIdOrderByVisitDateDesc(String petId);
}