package com.vermeg.bookstore.repository;

import com.vermeg.bookstore.entities.OrderItem;
import com.vermeg.bookstore.entities.OrderItemKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemKey> {
}
