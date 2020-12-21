package com.vermeg.bookstore.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private boolean state;
    @ManyToOne
    private User user;
    @OneToMany
    private Set<OrderItem> orderItemSet = new HashSet<OrderItem>();

}
