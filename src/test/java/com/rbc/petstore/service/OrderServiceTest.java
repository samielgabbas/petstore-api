package com.rbc.petstore.service;

import com.rbc.petstore.dto.OrderDTO;
import com.rbc.petstore.model.InventoryStatus;
import com.rbc.petstore.model.Order;
import com.rbc.petstore.model.OrderStatus;
import com.rbc.petstore.model.Pet;
import com.rbc.petstore.repository.OrderRepository;
import com.rbc.petstore.repository.PetRepository;
import com.rbc.petstore.util.OrderConverter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    PetService petService;

    @Mock
    PetRepository petRepository;

    @Mock
    OrderConverter orderConverter;

    Pet bella, bunny, chocky;
    Order placedOrder, delivredOrder;

    @Before
    public void setUp() {
        bella = new Pet(1L, "bella", InventoryStatus.AVAILABLE);
        bunny = new Pet(2L, "bunny", InventoryStatus.PENDING);
        chocky = new Pet(1L, "chocky", InventoryStatus.SOLD);

        placedOrder = new Order(1L, OrderStatus.PLACED, LocalDateTime.now(), bunny, 2);
        delivredOrder = new Order(2L, OrderStatus.DELIVERED, LocalDateTime.now(), chocky, 1);

        when(orderConverter.toDto(any())).thenCallRealMethod();
        when(orderConverter.ListToDto(any())).thenCallRealMethod();
    }

    @Test
    public void getAllOrders() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(placedOrder, delivredOrder));
        Iterable<OrderDTO> orders = orderService.getAllOrders();
        Assert.assertEquals(2, orders.spliterator().getExactSizeIfKnown());
    }

    @Test
    public void getOrder() {
        when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(placedOrder));

        OrderDTO orderDTO = orderService.getOrder(1L);
        Assert.assertEquals(placedOrder.getStatus(), orderDTO.getStatus());
    }

    @Test
    public void CreateOrder() {
        when(orderRepository.save(any())).thenReturn(placedOrder);
        when(petService.getPet(Mockito.anyLong())).thenReturn(bella);

        OrderDTO orderDTO = orderService.create(1L, 3);
        Assert.assertNotNull(orderDTO);
        Assert.assertEquals(placedOrder.getStatus(), orderDTO.getStatus());

        ArgumentCaptor<Pet> updatedPet = ArgumentCaptor.forClass(Pet.class);
        verify(petRepository, times(1)).save(updatedPet.capture());
        Assert.assertEquals(updatedPet.getValue().getStatus(),InventoryStatus.PENDING);
    }

    @Test
    public void updateOrder() {
    }

}