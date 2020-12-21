package com.vermeg.bookstore.exception;

public class OrderItemNotFoundException extends Exception {

    public OrderItemNotFoundException(String message){
        super(message);
    }
}
