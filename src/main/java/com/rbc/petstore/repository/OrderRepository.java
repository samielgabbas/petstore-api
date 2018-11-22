package com.rbc.petstore.repository;

import com.rbc.petstore.model.Order;
import org.springframework.data.repository.CrudRepository;

/**
 * {@link Order} CRUD operations
 */
public interface OrderRepository extends CrudRepository<Order, Long> {
}
