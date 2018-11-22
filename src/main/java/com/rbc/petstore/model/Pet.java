package com.rbc.petstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

/**
 * Pet class holding the pet entity information.
 */
@Entity
public class Pet {
    public Pet() {
        //  nothing to do here
    }

    public Pet(Long id, @NotEmpty String name, InventoryStatus status) {
        super();
        this.id = id;
        this.name = name;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PET_ID")
    private Long id;

    @NotEmpty
    private String name;

    @Enumerated(EnumType.STRING)
    private InventoryStatus status;

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
