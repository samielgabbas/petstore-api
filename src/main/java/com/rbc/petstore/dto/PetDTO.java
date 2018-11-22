package com.rbc.petstore.dto;

import javax.validation.constraints.NotEmpty;

import com.rbc.petstore.model.InventoryStatus;
import com.rbc.petstore.model.Pet;

/**
 * Pet Data Transfer Object
 */
public class PetDTO {

    private Long id;

    @NotEmpty
    private String name;

    private InventoryStatus status;

    public PetDTO() {
//		default constructor, nothing here for now
    }

    public PetDTO(Long id, String name, InventoryStatus status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InventoryStatus getStatus() {
        return status;
    }

    public void setStatus(InventoryStatus status) {
        this.status = status;
    }

}
