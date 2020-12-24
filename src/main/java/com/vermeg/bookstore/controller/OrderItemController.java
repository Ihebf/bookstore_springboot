package com.vermeg.bookstore.controller;

import com.vermeg.bookstore.entities.OrderItem;
import com.vermeg.bookstore.exception.BookNotFoundException;
import com.vermeg.bookstore.exception.OrderItemListEmptyException;
import com.vermeg.bookstore.exception.OrderItemNotFoundException;
import com.vermeg.bookstore.exception.UserNotFoundException;
import com.vermeg.bookstore.service.OrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderItems")
@AllArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @GetMapping
    public ResponseEntity<List<OrderItem>> getAllOrderItem(){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(orderItemService.getAllOrderItem());
        } catch (OrderItemListEmptyException e){
            return new ResponseEntity("There is no orderItem",HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{orderId}/{bookId}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Integer orderId, @PathVariable Integer bookId){
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(orderItemService.getOrderItemById(orderId,bookId));
        } catch (OrderItemNotFoundException e){
            return new ResponseEntity("There is no orderItem with this id "+orderId, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/add/order/{orderId}/book/{bookId}/user/{userId}")
    public ResponseEntity<OrderItem> createOrderItem(@PathVariable Integer orderId,
                                                     @PathVariable Integer bookId,
                                                     @PathVariable Integer userId){
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(orderItemService.createOrderItem(orderId,bookId,userId));
        } catch (UserNotFoundException e) {
            return new ResponseEntity("User not found with the id: "+userId,HttpStatus.NO_CONTENT);
        } catch (BookNotFoundException e) {
            return new ResponseEntity("Book not found with the id: "+bookId,HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/update/order/{orderId}/book/{bookId}/user/{userId}")
    public ResponseEntity<OrderItem> updateOrderItem(
            @PathVariable Integer orderId,
            @PathVariable Integer bookId,
            @RequestBody OrderItem orderItem){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(orderItemService.updateOrderItem(orderId,bookId,orderItem));
        } catch (OrderItemNotFoundException e) {
            return new ResponseEntity(
                    "OrderItem not found",
                    HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{orderId}/{bookId}")
    public ResponseEntity<OrderItem> deleteOrderItem(
            @PathVariable Integer orderId,
            @PathVariable Integer bookId){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(orderItemService.deleteOrderItem(orderId,bookId));
        } catch (OrderItemNotFoundException e) {
            return new ResponseEntity(
                    "OrderItem not found",
                    HttpStatus.NOT_FOUND);
        }
    }
}
