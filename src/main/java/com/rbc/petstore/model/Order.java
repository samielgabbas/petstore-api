package com.rbc.petstore.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "SHIP_DATE")
    private LocalDateTime shipDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PET_ID")
    private Pet pet;

    private int quantity;

    public Order() {
    }

    public Order(Long id, OrderStatus status, LocalDateTime shipDate, Pet pet, int quantity) {
        this.id =id;
        this.status = status;
        this.shipDate = shipDate;
        this.pet = pet;
        this.quantity = quantity;
    }

    public Order(OrderStatus status, LocalDateTime shipDate, Pet pet, int quantity) {
        this.status = status;
        this.shipDate = shipDate;
        this.pet = pet;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getShipDate() {
        return shipDate;
    }

    public void setShipDate(LocalDateTime shipDate) {
        this.shipDate = shipDate;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
