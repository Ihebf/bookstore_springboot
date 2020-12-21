package com.vermeg.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vermeg.bookstore.entities.Book;
import com.vermeg.bookstore.exception.BookListEmptyException;
import com.vermeg.bookstore.exception.BookNotFoundException;
import com.vermeg.bookstore.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(value = BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void getAllBooks() throws Exception {
        List<Book> books = new ArrayList<>();

        books.add(new Book(1,"title1","author1",100D,"releaseDate1"));
        books.add(new Book(2,"title2","author2",200D,"releaseDate2"));
        books.add(new Book(1,"title3","author3",300D,"releaseDate3"));

        when(bookService.getAllBooks())
                .thenReturn(books);

        mockMvc.perform(get("/book"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void getBookById() throws Exception {
        Book book = new Book(1,"title1","author1",100D,"releaseDate1");

        when(bookService.getBookById(anyInt()))
                .thenReturn(book);

        mockMvc.perform(get("/book/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").isNotEmpty())
                .andExpect(jsonPath("$.author").isNotEmpty())
                .andExpect(jsonPath("$.price").isNumber())
                .andExpect(jsonPath("$.releaseDate").isNotEmpty())
                .andDo(print());
    }

    @Test
    void createBook() throws Exception {
        Book book = new Book(1,"title1","author1",100D,"releaseDate1");

        when(bookService.createBook(any(Book.class)))
                .thenReturn(book);

        mockMvc.perform(
                post("/book/add")
                        .content(new ObjectMapper().writeValueAsString(book))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").isNotEmpty())
                .andExpect(jsonPath("$.author").isNotEmpty())
                .andExpect(jsonPath("$.price").isNumber())
                .andExpect(jsonPath("$.releaseDate").isNotEmpty())
                .andExpect(status().isCreated())
                .andDo(print());

    }

    @Test
    void updateBook() throws Exception {
        Book book = new Book(1,"title1","author1",100D,"releaseDate1");

        when(bookService.updateBook(anyInt(),any(Book.class)))
                .thenReturn(book);

        mockMvc.perform(
                put("/book/update/1")
                        .content(new ObjectMapper().writeValueAsString(book))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteBook() throws Exception {
        Book book = new Book(1,"title1","author1",100D,"releaseDate1");


        when(bookService.deleteBook(anyInt())).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.delete("/book/delete/1")
                .content(new ObjectMapper().writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
