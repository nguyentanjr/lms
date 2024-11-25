package com.example.demo.Services.Service;

import com.example.demo.DTO.AddBookDTO;
import com.example.demo.DTO.BookDTO;
import com.example.demo.Model.Book;
import com.example.demo.Model.Cart;
import com.example.demo.Model.User;
import com.example.demo.Model.UserBook;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface BookService {
    void saveBook(Book book);

    public List<Book> saveALlBooks(List<Book> books);

    public List<Book> getAllBooks();

    String saveBooksToJson();

    Book findBookByBookId(long bookId);


    List<Book> findBooksByTitle(String title);

    List<Book> findBookByAuthor(String authorName);

    List<Book> findBookByCategory(String category);

    void updateCopiesAvailable(long id, int copies);

    void fetchBooks(String title);

    Book retrieveBookDataFromAPI(JsonNode item);

    void removeBookById(long bookId);

    List<String> getBookSuggestion(String query);

    void addBook(AddBookDTO addBookDTO);

    int getBookCopies(long bookId);

    boolean checkUserHasBorrowedBook(long bookId);

    void saveBookBorrowedByUser(long bookId, int copies);

    void saveBookFromCart(@RequestBody List<BookDTO> bookDTOList,
                          @ModelAttribute("cart") Cart cart, HttpSession session);

    void removeBook(long bookId);

    void setHideBook(String status, long bookId);


    boolean checkBookHidden(long bookId);

    void editBook(Book book);
    void returnBook(long bookId);
}
