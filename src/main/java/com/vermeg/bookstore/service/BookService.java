package com.vermeg.bookstore.service;

import com.vermeg.bookstore.entities.Book;
import com.vermeg.bookstore.exception.BookListEmptyException;
import com.vermeg.bookstore.exception.BookNotFoundException;
import com.vermeg.bookstore.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;


    public List<Book> getAllBooks() throws BookListEmptyException {
        List<Book> books = bookRepository.findAll();
        if(books.isEmpty())
            throw new BookListEmptyException("There is no book");
        return books;
    }


    public Book getBookById(Integer bookId) throws BookNotFoundException {
        Book book = bookRepository.findById(bookId).orElseThrow(()->
                new BookNotFoundException(bookId.toString()));
        return book;
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Integer bookId, Book book) throws BookNotFoundException {
        Book _book = this.getBookById(bookId);

        _book.setAuthor(book.getAuthor());
        _book.setPrice(book.getPrice());
        _book.setTitle(book.getTitle());
        _book.setPrice(book.getPrice());

        return bookRepository.save(_book);
    }

    public Book deleteBook(Integer bookId) throws BookNotFoundException {
        Book book = this.getBookById(bookId);
        bookRepository.delete(book);
        return book;
    }
}
