package com.vermeg.bookstore.controller;

import com.vermeg.bookstore.entities.OrderItem;
import com.vermeg.bookstore.exception.*;
import com.vermeg.bookstore.service.OrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderItems")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
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

    @GetMapping("/{orderId}/{bookId}/{username}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Integer orderId, @PathVariable Integer bookId,@PathVariable String username){
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(orderItemService.getOrderItemById(orderId,bookId,username));
        } catch (OrderItemNotFoundException | OrderNotFoundException | BookNotFoundException e){
            return new ResponseEntity("There is no item with this id "+orderId, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/add/book/{bookId}/user/{username}")
    public ResponseEntity<OrderItem> createOrderItem(@PathVariable Integer bookId,
                                                     @PathVariable String username){
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(orderItemService.createOrderItem(bookId,username));
        } catch (UserNotFoundException e) {
            return new ResponseEntity("User not found with the name: "+username,HttpStatus.NO_CONTENT);
        } catch (BookNotFoundException e) {
            return new ResponseEntity("Book not found with the id: "+bookId,HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/update/order/{orderId}/book/{bookId}/user/{userId}")
    public ResponseEntity updateOrderItem(
            @PathVariable Integer orderId,
            @PathVariable Integer bookId,
            @RequestBody OrderItem orderItem){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(orderItemService.updateOrderItem(orderId,bookId,orderItem));
        } catch (OrderItemNotFoundException e) {
            return new ResponseEntity<>(
                    "Item not found",
                    HttpStatus.NOT_FOUND);
        } catch (OrderNotFoundException e) {
            return new ResponseEntity<>(
                    "Order not found",
                    HttpStatus.NOT_FOUND);
        } catch (BookNotFoundException e) {
            return new ResponseEntity<>(
                    "Book not found",
                    HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{orderItemId}")
    public ResponseEntity deleteOrderItem(
            @PathVariable Integer orderItemId){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(orderItemService.deleteOrderItem(orderItemId));
        } catch (OrderItemNotFoundException e) {
            return new ResponseEntity<>(
                    "Item not found",
                    HttpStatus.NOT_FOUND);
        }
    }
}
