package com.rbc.petstore.util;

import com.rbc.petstore.dto.PetDTO;
import com.rbc.petstore.model.Pet;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PetConvertor {
    private PetConvertor() {
        throw new IllegalStateException("Utility class");
    }

    public static PetDTO toDto(Pet pet) {
        return new PetDTO(pet.getId(), pet.getName(), pet.getStatus());
    }

    public static Iterable<PetDTO> toDtoList(Iterable<Pet> pets) {
        return StreamSupport
                .stream(pets.spliterator(), false)
                .map(PetConvertor::toDto)
                .collect(Collectors.toList());
    }

    public static Pet fromDto(PetDTO pet) {
        return new Pet(pet.getId(), pet.getName(), pet.getStatus());
    }
}
