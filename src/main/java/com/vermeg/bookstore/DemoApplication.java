package com.vermeg.bookstore;

import com.vermeg.bookstore.entities.Book;
import com.vermeg.bookstore.entities.Order;
import com.vermeg.bookstore.repository.BookRepository;
import com.vermeg.bookstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Date;
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private OrderRepository orderRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        bookRepository.save(new Book(null, "title1","author1",100.0,"releaseDate1"));
        bookRepository.save(new Book(null, "title2","author2",200.0,"releaseDate2"));
        bookRepository.save(new Book(null, "title3","author3",300.0,"releaseDate3"));
        bookRepository.findAll().forEach(book -> System.out.println(book));

        orderRepository.save(new Order(null,5,9));
        orderRepository.save(new Order(null,1,3));
        orderRepository.save(new Order(null,10,5));
        orderRepository.findAll().forEach(order -> System.out.println(order));

    }
}
