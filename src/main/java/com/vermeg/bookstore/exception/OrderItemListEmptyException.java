package com.vermeg.bookstore.exception;

public class OrderItemListEmptyException extends Exception {
    public OrderItemListEmptyException(String message){
        super(message);
    }
}
