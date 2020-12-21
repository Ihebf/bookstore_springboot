package com.vermeg.bookstore;

import com.vermeg.bookstore.entities.Book;
import com.vermeg.bookstore.entities.Order;
import com.vermeg.bookstore.entities.OrderItem;
import com.vermeg.bookstore.repository.BookRepository;
import com.vermeg.bookstore.repository.OrderItemRepository;
import com.vermeg.bookstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) {
//        Book book1 = new Book(null, "title1","author1",100.0,"releaseDate1");
//        Book book2 = new Book(null, "title2","author2",200.0,"releaseDate2");
//        Book book3 = new Book(null, "title3","author3",300.0,"releaseDate3");
//
//        bookRepository.save(book1);
//        bookRepository.save(book2);
//        bookRepository.save(book3);
//
//        OrderItem orderItem = new OrderItem();
//
//        Order order1 = new Order();
//        order1.setQuantity(5);
//        order1.setUnitPrice(book1.getPrice());
//        order1.setBook(book1);
//
//        List<Order> orders = new ArrayList<>();
//        orders.add(order1);
//        orderItem.setOrders(orders);
//
//
//        orderRepository.save(order1);
//        orderItemRepository.save(orderItem);
    }
}
