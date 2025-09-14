package com.petcare.repository;

import com.petcare.model.Pet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends MongoRepository<Pet, String> {
    
    // Find pets by owner ID
    List<Pet> findByOwnerId(String ownerId);
    
    // Find pet by name and owner ID
    Optional<Pet> findByNameAndOwnerId(String name, String ownerId);
    
    // Find pets by species
    List<Pet> findBySpeciesIgnoreCase(String species);
    
    // Find pets by breed
    List<Pet> findByBreedIgnoreCase(String breed);
    
    // Find pets by species and breed
    List<Pet> findBySpeciesIgnoreCaseAndBreedIgnoreCase(String species, String breed);
    
    // Find pets by age range
    @Query("{'age': {'$gte': ?0, '$lte': ?1}}")
    List<Pet> findByAgeBetween(Integer minAge, Integer maxAge);
    
    // Count pets by owner
    Long countByOwnerId(String ownerId);
    
    // Check if pet exists by name and owner
    boolean existsByNameAndOwnerId(String name, String ownerId);
    
    // Find pets by multiple criteria
    @Query("{'$and': [" +
           "{'$or': [{'species': null}, {'species': {'$regex': ?0, '$options': 'i'}}]}," +
           "{'$or': [{'breed': null}, {'breed': {'$regex': ?1, '$options': 'i'}}]}," +
           "{'$or': [{'ownerId': null}, {'ownerId': ?2}]}" +
           "]}")
    List<Pet> findPetsByCriteria(String species, String breed, String ownerId);
}