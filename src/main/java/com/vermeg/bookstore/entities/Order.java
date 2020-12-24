package com.vermeg.bookstore.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private boolean state;
    private double totalPrice;
    @ManyToOne
    private User user;
    @OneToMany
    private List<OrderItem> orderItem;

}
