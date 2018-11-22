package com.rbc.petstore.rest;

import com.google.gson.Gson;
import com.rbc.petstore.PetstoreApplication;
import com.rbc.petstore.dto.OrderDTO;
import com.rbc.petstore.model.Order;
import com.rbc.petstore.model.OrderStatus;
import com.rbc.petstore.repository.OrderRepository;
import com.rbc.petstore.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PetstoreApplication.class)
@Transactional
public class OrderResourceTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderResource orderResource;

    private MockMvc restMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        this.restMvc = MockMvcBuilders.standaloneSetup(orderResource).build();
    }

    @Test
    public void getAll() throws Exception {
        restMvc.perform(get("/api/order"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void create() throws Exception {
        OrderDTO order = getValidOrderDTO();
        restMvc.perform(post("/api/order/1/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status", is(order.getStatus().toString())));

    }

    @Test
    public void getOrder() throws Exception {
        Order order = orderRepository.findById(1L).get();
        restMvc.perform(get("/api/order/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId().toString()))
                .andExpect(jsonPath("$.status").value(order.getStatus().toString()));
    }

    private OrderDTO getValidOrderDTO() {
        OrderDTO order = new OrderDTO(null, LocalDateTime.now(), OrderStatus.PLACED, 1L, 2);
        return order;
    }

    private String getPetJSON(OrderDTO order) {
        Gson gson = new Gson();
        String json = gson.toJson(order);
        return json;
    }

    @Test
    public void delete() {
    }

}