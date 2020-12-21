package com.vermeg.bookstore.repository;

import com.vermeg.bookstore.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
