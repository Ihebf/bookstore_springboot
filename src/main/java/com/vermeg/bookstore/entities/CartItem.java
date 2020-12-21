package com.vermeg.bookstore.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CartItem {

    private Book book;
    private int quantity;
    private double price;
}
