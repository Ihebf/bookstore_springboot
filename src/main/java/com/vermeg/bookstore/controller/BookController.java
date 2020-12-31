package com.vermeg.bookstore.controller;

import com.vermeg.bookstore.entities.Book;
import com.vermeg.bookstore.exception.BookListEmptyException;
import com.vermeg.bookstore.exception.BookNotFoundException;
import com.vermeg.bookstore.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(bookService.getAllBooks());
        } catch (BookListEmptyException e) {
            return new ResponseEntity("There is no book",HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer bookId){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(bookService.getBookById(bookId));
        } catch (BookNotFoundException e) {
            return new ResponseEntity(
                    "Book not found",
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        try{
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(bookService.createBook(book));
        }catch (Exception e){
          return new ResponseEntity(
                  "The server has encountered a situation that it cannot handle.",
                  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable Integer bookId, @RequestBody Book book){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(bookService.updateBook(bookId,book));
        } catch (BookNotFoundException e) {
            return new ResponseEntity(
                    "Book not found",
                    HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<Book> deleteBook(@PathVariable Integer bookId){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(bookService.deleteBook(bookId));
        }catch (BookNotFoundException e){
            return new ResponseEntity(
                    "Book not found",
                    HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity(
                    "The server has encountered a situation that it cannot handle.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
