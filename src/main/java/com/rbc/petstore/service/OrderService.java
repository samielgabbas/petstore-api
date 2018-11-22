package com.rbc.petstore.service;

import com.rbc.petstore.dto.OrderDTO;
import com.rbc.petstore.model.InventoryStatus;
import com.rbc.petstore.model.Order;
import com.rbc.petstore.model.OrderStatus;
import com.rbc.petstore.model.Pet;
import com.rbc.petstore.repository.OrderRepository;
import com.rbc.petstore.repository.PetRepository;
import com.rbc.petstore.util.OrderConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    PetService petService;
    @Autowired
    PetRepository petRepository;
    @Autowired
    OrderConverter orderConverter;

    public Iterable<OrderDTO> getAllOrders() {
        Iterable<Order> orders = orderRepository.findAll();
        return orderConverter.ListToDto(orders);
    }

    public OrderDTO getOrder(Long id) {
        Order order = orderRepository.findById(id).get();
        return orderConverter.toDto(order);
    }

    public OrderDTO create(Long petId, int quantity) {
        Pet pet = petService.getPet(petId);
        Order order = new Order(OrderStatus.PLACED, LocalDateTime.now(), pet, quantity);
        pet.setStatus(InventoryStatus.PENDING);
        petRepository.save(pet);
        Order createdOrder = orderRepository.save(order);
        return orderConverter.toDto(createdOrder);
    }

    public OrderDTO updateOrder(Long id, Order order) {
        order.setId(id);
        return orderConverter.toDto(orderRepository.save(order));
    }

    public void deleteOrder(Long id) {
        Optional<Order> toDelete = orderRepository.findById(id);

        if (!toDelete.isPresent()) {
            throw new InvalidParameterException("Order not found");
        }

        Order order = orderRepository.findById(id).get();
        if (OrderStatus.PLACED.equals(order.getStatus())) {
            Pet pet = order.getPet();
            pet.setStatus(InventoryStatus.AVAILABLE);
            petRepository.save(pet);
            orderRepository.deleteById(id);
        }
    }

}
