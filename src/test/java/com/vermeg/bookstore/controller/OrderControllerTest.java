package com.vermeg.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vermeg.bookstore.entities.Order;
import com.vermeg.bookstore.entities.OrderItem;
import com.vermeg.bookstore.entities.User;
import com.vermeg.bookstore.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void getAllOrders() throws Exception {
        List<Order> orders = new ArrayList<>();

        orders.add(new Order(1,false,new User(),new HashSet<OrderItem>()));
        orders.add(new Order(2,false,new User(),new HashSet<OrderItem>()));
        orders.add(new Order(3,false,new User(),new HashSet<OrderItem>()));

        when(orderService.getAllOrder())
                .thenReturn(orders);

        mockMvc.perform(get("/order"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void getOrderById() throws Exception {
        Order order = new Order(1,false,new User(),new HashSet<OrderItem>());

        when(orderService.getOrderById(anyInt()))
                .thenReturn(order);

        mockMvc.perform(get("/order/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.state").isBoolean())
                .andExpect(jsonPath("$.user").exists())
                .andExpect(jsonPath("$.orderItemSet").isArray())
                .andDo(print());
    }

    @Test
    void getOrderItems() throws Exception {
        Set<OrderItem> orderItems = new HashSet<>();

        when(orderService.getOrderItems(anyInt()))
                .thenReturn(orderItems);

        System.out.println(anyInt());

        mockMvc.perform(get("/order/1/orderItems"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void createOrder() throws Exception {
        Order order = new Order(1,false,new User(),new HashSet<OrderItem>());

        when(orderService.createOrder(any(Order.class)))
                .thenReturn(order);

        mockMvc.perform(
                post("/order/add")
                        .content(new ObjectMapper().writeValueAsString(order))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());

    }

    @Test
    void updateOrder() throws Exception {
        Order order = new Order(1,false,new User(),new HashSet<OrderItem>());

        when(orderService.updateOrder(anyInt(),any(Order.class)))
                .thenReturn(order);

        mockMvc.perform(
                put("/order/update/1")
                        .content(new ObjectMapper().writeValueAsString(order))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteOrder() throws Exception {
        Order order = new Order(1,false,new User(),new HashSet<OrderItem>());


        when(orderService.deleteOrder(anyInt())).thenReturn(order);

        mockMvc.perform(MockMvcRequestBuilders.delete("/order/delete/1")
                .content(new ObjectMapper().writeValueAsString(order))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
