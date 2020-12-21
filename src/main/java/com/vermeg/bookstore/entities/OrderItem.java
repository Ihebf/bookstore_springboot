package com.vermeg.bookstore.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class OrderItem {

    @EmbeddedId
    private OrderItemKey id;
    private int quantity;
    private double price;
    @ManyToOne
    private Book book;
    @ManyToOne
    private Order order;
}
