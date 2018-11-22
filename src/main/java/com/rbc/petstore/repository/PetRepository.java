package com.rbc.petstore.repository;

import org.springframework.data.repository.CrudRepository;
import com.rbc.petstore.model.Pet;

/**
 * {@link Pet} CRUD operations
 */
public interface PetRepository extends CrudRepository<Pet, Long> {
}
