package com.example.demo.Controller;

import com.example.demo.DTO.BorrowRequest;
import com.example.demo.Model.Book;
import com.example.demo.Model.Genre;
import com.example.demo.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/createBook")
    public Book createBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @PostMapping("/createBooks")
    public List<Book> createBooks(@RequestBody List<Book> books) {
        return bookService.saveALlBooks(books);
    }

    @GetMapping("/retrieveAllBooks")
    public List<Book> retrieveAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/retrieveBooksByName")
    public List<Book> retrieveBooksByName(@RequestParam String name) {
        return bookService.findBooksByName(name);
    }

    @GetMapping("/retrieveBookById/{id}")
    public Book retrieveBookById(@PathVariable long id) {
        return bookService.findBookById(id);
    }

    @GetMapping("/retrieveBooksByGenre/{genre}")
    public List<Book> retrieveBookByGenre(@PathVariable Genre genre) {
        return bookService.findBooksByGenre(genre);
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
