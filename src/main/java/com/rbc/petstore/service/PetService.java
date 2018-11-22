package com.rbc.petstore.service;

import java.security.InvalidParameterException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rbc.petstore.dto.PetDTO;
import com.rbc.petstore.model.Pet;
import com.rbc.petstore.repository.PetRepository;

/**
 * Pet Service
 *
 * @see PetDTO
 */
@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    /**
     * Get all Pets
     *
     * @return a pets
     */
    public Iterable<Pet> getAllPets() {
        return petRepository.findAll();
    }


    /**
     * Find pet by ID
     *
     * @param id identifier of the pet to find
     * @return a {@link Pet} instance if a match is found, null otherwise
     */
    public Pet getPet(Long id) {
        return petRepository.findById(id).orElse(null);
    }

    /**
     * Add a new pet to the store
     *
     * @param pet entity to create
     * @return the created {@link Pet}
     */
    public Pet createPet(Pet pet) {
        if (pet.getId() != null) {
            throw new InvalidParameterException("Cannot create an already existing pet");
        }
        return petRepository.save(pet);
    }

    /**
     * Deletes a pet
     *
     * @param id Pet id to delete
     */
    public void deletePet(Long id) {
        Optional<Pet> toDelete = petRepository.findById(id);
        if (!toDelete.isPresent()) {
            throw new InvalidParameterException("Pet not found");
        }

        petRepository.deleteById(id);
    }

    public Pet updatePet(Pet pet, Long petId){
        pet.setId(petId);
        return petRepository.save(pet);
    }
}
