package com.vermeg.bookstore.repository;

import com.vermeg.bookstore.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
}
