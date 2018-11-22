package com.rbc.petstore.rest;

import com.rbc.petstore.dto.PetDTO;
import com.rbc.petstore.model.Pet;
import com.rbc.petstore.service.PetService;
import com.rbc.petstore.util.PetConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST operations for {@link PetDTO} resource
 */
//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/pet")
public class PetResource {

    private final PetService petService;

    @Autowired
    public PetResource(PetService petService) {
        this.petService = petService;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(List.class, new StringToInventoryStatusConverter());
    }

    /**
     * Get all pets
     *
     * @return a {@link HttpStatus#OK} on the status and a PetDTO instance in the response body if the id exists, {@link HttpStatus#BAD_REQUEST} on status otherwise
     */
    @GetMapping("")
    public ResponseEntity<Iterable<PetDTO>> getAll() {
        Iterable<Pet> pets = petService.getAllPets();
        return new ResponseEntity<>(PetConvertor.toDtoList(pets), HttpStatus.OK);
    }

    /**
     * Get one pet by id.
     *
     * @param petId pet identifier
     * @return a {@link HttpStatus#OK} on the status and a PetDTO instance in the response body if the id exists, {@link HttpStatus#BAD_REQUEST} on status otherwise
     */
    @GetMapping("/{petId}")
    public ResponseEntity<PetDTO> getOne(@PathVariable(name = "petId") Long petId) {
        Pet pet = petService.getPet(petId);
        if (pet == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(PetConvertor.toDto(pet), HttpStatus.OK);
    }

    /**
     * Add a new pet to the store
     *
     * @param petDto to create
     * @return if successfully it returns {@link HttpStatus#CREATED} on status and the created {@link PetDTO}, {@link HttpStatus#BAD_REQUEST} on status otherwise
     */
    @PostMapping("")
    public ResponseEntity<PetDTO> create(@Valid @RequestBody PetDTO petDto) {
        try {
            Pet pet = PetConvertor.fromDto(petDto);
            Pet createdPet = petService.createPet(pet);

            return new ResponseEntity<>(PetConvertor.toDto(createdPet), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes a pet
     *
     * @param petId pet identifier
     * @return if successfully a {@link HttpStatus#OK} on the status,  {@link HttpStatus#BAD_REQUEST} otherwise
     */
    @DeleteMapping("/{petId}")
    public ResponseEntity<Void> delete(@PathVariable Long petId) {
        try {
            petService.deletePet(petId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
