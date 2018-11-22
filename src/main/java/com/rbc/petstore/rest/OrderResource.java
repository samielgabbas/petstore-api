package com.rbc.petstore.rest;

import com.rbc.petstore.dto.OrderDTO;
import com.rbc.petstore.model.Order;
import com.rbc.petstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/order")
public class OrderResource {

    @Autowired
    OrderService orderService;

    @GetMapping("")
    public Iterable<OrderDTO> getAll(){
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public OrderDTO getOrder(@PathVariable Long orderId){
        return orderService.getOrder(orderId);
    }

    @PostMapping("/{petId}/{quantity}")
    public OrderDTO create(@PathVariable Long petId, @PathVariable int quantity){
        return orderService.create(petId, quantity);
    }

    @DeleteMapping("/{orderId}")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<Void> delete(@PathVariable Long orderId){
        try {
            orderService.deleteOrder(orderId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
