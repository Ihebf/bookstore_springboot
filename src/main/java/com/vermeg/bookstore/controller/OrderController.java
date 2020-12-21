package com.vermeg.bookstore.controller;

import com.vermeg.bookstore.entities.Order;
import com.vermeg.bookstore.entities.OrderItem;
import com.vermeg.bookstore.exception.OrderListEmptyException;
import com.vermeg.bookstore.exception.OrderNotFoundException;
import com.vermeg.bookstore.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrder(){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(orderService.getAllOrder());
        } catch (OrderListEmptyException e){
            return new ResponseEntity("There is no order",HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer orderId){
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(orderService.getOrderById(orderId));
        } catch (OrderNotFoundException e){
            return new ResponseEntity("There is no order with this id "+orderId, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("{orderId}/orderItems")
    public ResponseEntity<Set<OrderItem>> getOrderItems(@PathVariable Integer orderId){
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(orderService.getOrderItems(orderId));
        } catch (OrderNotFoundException e){
            return new ResponseEntity("There is no order with this id "+orderId, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        try{
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(orderService.createOrder(order));
        }catch (Exception e){
            return new ResponseEntity("The server has encountered a situation that it cannot handle.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Integer orderId, @RequestBody Order order){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(orderService.updateOrder(orderId,order));
        } catch (OrderNotFoundException e) {
            return new ResponseEntity(
                    "Order not found",
                    HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<Order> deleteOrder(@PathVariable Integer orderId){
        try {
            orderService.deleteOrder(orderId);
            return new ResponseEntity(
                    "Order deleted",
                    HttpStatus.NO_CONTENT);
        }catch (OrderNotFoundException e){
            return new ResponseEntity(
                    "Order not found",
                    HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity(
                    "The server has encountered a situation that it cannot handle.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
