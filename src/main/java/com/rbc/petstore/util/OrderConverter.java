package com.rbc.petstore.util;

import com.rbc.petstore.dto.OrderDTO;
import com.rbc.petstore.model.Order;
import com.rbc.petstore.model.Pet;
import com.rbc.petstore.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class OrderConverter {

    @Autowired
    PetRepository petRepository;

    public OrderConverter() {
    }

    public OrderDTO toDto(Order order){
        return new OrderDTO(order.getId(), order.getShipDate(), order.getStatus(), order.getPet().getId(), order.getQuantity());
    }

    public Iterable<OrderDTO> ListToDto(Iterable<Order> orders){
        return StreamSupport
                .stream(orders.spliterator(), false)
                .map(order -> toDto(order))
                .collect(Collectors.toList());
    }

    public Order fromDto(OrderDTO orderDTO){
        Pet pet = petRepository.findById(orderDTO.getPetId()).get();
        return new Order(orderDTO.getId(), orderDTO.getStatus(), orderDTO.getShipDate(), pet, orderDTO.getQuantity());
    }
}
