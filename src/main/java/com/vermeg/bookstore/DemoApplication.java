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
public class DemoApplication{



    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
