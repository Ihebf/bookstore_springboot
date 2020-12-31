package com.vermeg.bookstore.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class OrderItem {

    @Id @GeneratedValue
    private Integer id;
    private int quantity;
    private double price;
    @ManyToOne
    private Book book;
    @ManyToOne
    @JsonIgnore
    private Order order;
}
