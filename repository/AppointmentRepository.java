package com.petcare.repository;

import com.petcare.model.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, String> {
    
    // Find appointments by pet ID
    List<Appointment> findByPetId(String petId);
    
    // Find appointments by owner ID
    List<Appointment> findByOwnerId(String ownerId);
    
    // Find appointments by vet ID
    List<Appointment> findByVetId(String vetId);
    
    // Find appointments by status
    List<Appointment> findByStatus(Appointment.AppointmentStatus status);
    
    // Find appointments by date range
    @Query("{'appointmentDateTime': {'$gte': ?0, '$lte': ?1}}")
    List<Appointment> findByAppointmentDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Find appointments by vet and date range
    @Query("{'vetId': ?0, 'appointmentDateTime': {'$gte': ?1, '$lte': ?2}}")
    List<Appointment> findByVetIdAndAppointmentDateTimeBetween(String vetId, LocalDateTime startDate, LocalDateTime endDate);
    
    // Find upcoming appointments
    @Query("{'appointmentDateTime': {'$gte': ?0}}")
    List<Appointment> findUpcomingAppointments(LocalDateTime currentDate);
    
    // Find appointments by pet and date range
    @Query("{'petId': ?0, 'appointmentDateTime': {'$gte': ?1, '$lte': ?2}}")
    List<Appointment> findByPetIdAndAppointmentDateTimeBetween(String petId, LocalDateTime startDate, LocalDateTime endDate);
    
    // Count appointments by status
    long countByStatus(Appointment.AppointmentStatus status);
}