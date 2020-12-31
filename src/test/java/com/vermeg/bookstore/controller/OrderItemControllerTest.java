package com.vermeg.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vermeg.bookstore.entities.Book;
import com.vermeg.bookstore.entities.Order;
import com.vermeg.bookstore.entities.OrderItem;
import com.vermeg.bookstore.service.OrderItemService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = OrderItemController.class)
class OrderItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderItemService orderItemService;

    @Test
    void getAllOrderItem() throws Exception {
        List<OrderItem> orderItems = new ArrayList<>();

        orderItems.add(new OrderItem(1,5,100D,new Book(),new Order()));
        orderItems.add(new OrderItem(2,4,200D,new Book(),new Order()));
        orderItems.add(new OrderItem(3,3,50D,new Book(),new Order()));

        Mockito.when(orderItemService.getAllOrderItem())
                .thenReturn(orderItems);

        mockMvc.perform(get("/orderItems"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getOrderItemById() throws Exception{
        OrderItem orderItem = new OrderItem(3,3,50D,new Book(),new Order());


        when(orderItemService.getOrderItemById(anyInt(),anyInt(),anyString()))
                .thenReturn(orderItem);

        mockMvc.perform(get("/orderItems/1/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void createOrderItem() throws Exception {
        OrderItem orderItem = new OrderItem(3,3,50D,new Book(),new Order());

        when(orderItemService.createOrderItem(anyInt(),anyString()))
                .thenReturn(orderItem);

        mockMvc.perform(
                post("/orderItems/add")
                        .content(new ObjectMapper().writeValueAsString(orderItem))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void updateOrderItem() throws Exception{
        OrderItem orderItem = new OrderItem(3,3,50D,new Book(),new Order());

        when(orderItemService.updateOrderItem(anyInt(),anyInt(),any(OrderItem.class)))
                .thenReturn(orderItem);

        mockMvc.perform(
                put("/orderItems/update/1/1")
                        .content(new ObjectMapper().writeValueAsString(orderItem))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteOrderItem() throws Exception{
        OrderItem orderItem = new OrderItem(3,3,50D,new Book(),new Order());

        when(orderItemService.deleteOrderItem(anyInt()))
                .thenReturn(orderItem);

        mockMvc.perform(
                delete("/orderItems/delete/1/1")
                        .content(new ObjectMapper().writeValueAsString(orderItem))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }
}
