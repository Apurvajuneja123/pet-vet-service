package com.petcare.repository;

import com.petcare.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    
    // Find user by username
    Optional<User> findByUsername(String username);
    
    // Find user by email
    Optional<User> findByEmail(String email);
    
    // Find user by username or email
    @Query("{'$or': [{'username': ?0}, {'email': ?0}]}")
    Optional<User> findByUsernameOrEmail(String usernameOrEmail);
    
    // Find users by role
    List<User> findByRole(String role);
    
    // Find users by first name and last name
    List<User> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
    
    // Find users by phone number
    Optional<User> findByPhoneNumber(String phoneNumber);
    
    // Check if username exists
    boolean existsByUsername(String username);
    
    // Check if email exists
    boolean existsByEmail(String email);
    
    // Check if phone number exists
    boolean existsByPhoneNumber(String phoneNumber);
    
    // Find active users
    @Query("{'active': true}")
    List<User> findActiveUsers();
    
    // Find users by role and active status
    @Query("{'role': ?0, 'active': ?1}")
    List<User> findByRoleAndActive(String role, Boolean active);
    
    // Search users by name
    @Query("{'$or': [{'firstName': {'$regex': ?0, '$options': 'i'}}, {'lastName': {'$regex': ?0, '$options': 'i'}}]}")
    List<User> searchUsersByName(String searchTerm);
    
    // Find all vets
    @Query("{'role': 'VET', 'active': true}")
    List<User> findAllActiveVets();
    
    // Find all owners
    @Query("{'role': 'OWNER', 'active': true}")
    List<User> findAllActiveOwners();
    
    // Count users by role
    @Query(value = "{'role': ?0}", count = true)
    Long countByRole(String role);
}