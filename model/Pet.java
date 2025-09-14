package com.petcare.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "pets")
public class Pet {
    @Id
    private String id;
    
    private String name;
    private String species;
    private String breed;
    private int age;
    private double weight;
    private String color;
    private String gender;
    private LocalDate dateOfBirth;
    
    private String ownerId; // Reference to User
    
    private String microchipNumber;
    private boolean isNeutered;
    private String specialNotes;
    
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Constructors, getters, and setters
    public Pet() {}

    public Pet(String name, String species, String breed, int age, String ownerId) {
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.age = age;
        this.ownerId = ownerId;
    }

    // All getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }
    
    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }
    
    public String getMicrochipNumber() { return microchipNumber; }
    public void setMicrochipNumber(String microchipNumber) { this.microchipNumber = microchipNumber; }
    
    public boolean isNeutered() { return isNeutered; }
    public void setNeutered(boolean neutered) { isNeutered = neutered; }
    
    public String getSpecialNotes() { return specialNotes; }
    public void setSpecialNotes(String specialNotes) { this.specialNotes = specialNotes; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}