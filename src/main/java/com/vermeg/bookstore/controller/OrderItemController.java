package com.vermeg.bookstore.controller;

import com.vermeg.bookstore.entities.OrderItem;
import com.vermeg.bookstore.entities.OrderItemKey;
import com.vermeg.bookstore.exception.OrderItemListEmptyException;
import com.vermeg.bookstore.exception.OrderItemNotFoundException;
import com.vermeg.bookstore.service.OrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderItemItems")
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

    @GetMapping("/{orderItemId}/{bookId}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Integer orderItemId, @PathVariable Integer bookId){
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(orderItemService.getOrderItemById(orderItemId,bookId));
        } catch (OrderItemNotFoundException e){
            return new ResponseEntity("There is no orderItem with this id "+orderItemId, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItem orderItem){
        try{
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(orderItemService.createOrderItem(orderItem));
        }catch (Exception e){
            return new ResponseEntity("The server has encountered a situation that it cannot handle.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{orderItemId}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable OrderItemKey orderItemId, @RequestBody OrderItem orderItem){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(orderItemService.updateOrderItem(orderItemId,orderItem));
        } catch (OrderItemNotFoundException e) {
            return new ResponseEntity(
                    "OrderItem not found",
                    HttpStatus.NOT_FOUND);
        }
    }
}
