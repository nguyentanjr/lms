package com.example.demo.Services.Service;

import com.example.demo.Model.Book;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface BookService {
    void saveBook(Book book);

    List<Book> saveALlBooks(List<Book> books);

    List<Book> getAllBooks();

    Book findBookByBookId(long bookId);

    List<Book> findBooksByTitle(String title);

    List<Book> findBookByAuthor(String authorName);

    void updateCopiesAvailable(long id,int copies);

    void fetchBooks(String title);
    Book retrieveBookDataFromAPI(JsonNode item);
}
