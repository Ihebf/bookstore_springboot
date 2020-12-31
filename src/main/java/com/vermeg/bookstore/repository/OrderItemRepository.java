package com.vermeg.bookstore.repository;

import com.vermeg.bookstore.entities.Book;
import com.vermeg.bookstore.entities.Order;
import com.vermeg.bookstore.entities.OrderItem;
import com.vermeg.bookstore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    Optional<OrderItem> findByOrderAndBook(Order order, Book book);
}
