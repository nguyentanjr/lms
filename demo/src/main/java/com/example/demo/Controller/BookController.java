package com.example.demo.Controller;

import com.example.demo.DTO.BorrowRequest;
import com.example.demo.Model.Book;
import com.example.demo.Model.Genre;
import com.example.demo.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/createBook")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book bookCreated = bookService.saveBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PostMapping("/createBooks")
    public ResponseEntity<List<Book>> createBooks(@RequestBody List<Book> books) {
        List<Book> bookList = bookService.saveALlBooks(books);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookList);
    }

    @GetMapping("/retrieveAllBooks")
    public ResponseEntity<List<Book>> retrieveAllBooks(@RequestBody List<Book> books) {
        List<Book> bookList = bookService.getAllBooks();
        return ResponseEntity.status(HttpStatus.CREATED).body(bookList);
    }

    @GetMapping("/retrieveBooksByName")
    public ResponseEntity<List<Book>> retrieveBooksByName(@RequestParam String bookName) {
        List<Book> bookList = bookService.findBooksByName(bookName);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookList);
    }

    @GetMapping("/retrieveBookById")
    public ResponseEntity<Book> retrieveBookById(@RequestParam long bookId) {
        Book book = bookService.findBookById(bookId);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @GetMapping("/retrieveBooksByGenre")
    public ResponseEntity<List<Book>> retrieveBooksByGenre(@RequestParam Genre genre) {
        List<Book> bookList = bookService.findBooksByGenre(genre);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookList);
    }
    @GetMapping("/retrieveBooksByAuthorName")
    public List<Book> retrieveBooksByAuthorName(@RequestParam String author_name) {
        return bookService.findBooksByAuthorName(author_name);
    }
    @GetMapping("/retrieveBooksByPublishedYear")
    public List<Book> retrieveBooksByPublishedYear(@RequestParam int publishedYear) {
        return bookService.findBooksByPublishedYear(publishedYear);
    }
    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBooks(@RequestBody BorrowRequest borrowRequest) {
        try {
            Book book = bookService.borrowBooks(borrowRequest.getId(),borrowRequest.getQuantity());
            return ResponseEntity.ok("Successfully borrowed book: " + book.getName());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

     //Endpoint để xóa cột
//    @PostMapping("/drop-column")
//    public String dropColumn() {
//        bookService.dropColumn();
//        return "Column dropped successfully";
//    }


}
