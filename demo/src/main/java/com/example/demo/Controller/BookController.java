package com.example.demo.Controller;

import com.example.demo.DTO.BorrowRequest;
import com.example.demo.Model.Book;
import com.example.demo.Model.Enum.Genre;
import com.example.demo.Model.User;
import com.example.demo.Model.UserBook;
import com.example.demo.Respository.BookRepository;
import com.example.demo.Respository.UserBookRepository;
import com.example.demo.Service.BookService;
import com.example.demo.Service.UserBookService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserBookService userBookService;

    @GetMapping("/book_list")
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("genres", bookService.getAllGenre());
        return "book_list";
    }

    @GetMapping("/book_list/get_copies/{bookId}")
    public ResponseEntity<Integer> getBookCopies(@PathVariable long bookId) {
        Book book = bookService.findBookByBookId(bookId);
        int copiesAvailable = book.getCopiesAvailable();
        return ResponseEntity.ok(copiesAvailable);
    }

    @GetMapping("/book_list/borrowed_check/{bookId}")
    public ResponseEntity<Boolean> checkUserHasBorrowedBook(@PathVariable long bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(authentication.getName()).get();
        return ResponseEntity.ok(userBookService.hasUserBorrowedBook(user.getId(), bookId));
    }

    @GetMapping("/book_list/borrow/{book_id}")
    public ResponseEntity<String> borrowBook(@PathVariable long book_id, int copies) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Book book = bookService.findBookByBookId(book_id);
        User user = userService.findUserByUserName(authentication.getName()).get();
        if (userBookService.hasUserBorrowedBook(user.getId(), book.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("You had already borrow this book!");
        }
        if (book.getCopiesAvailable() > 0) {
            book.setCopiesAvailable(copies);
            ;
            userBookService.save(new UserBook(user, book));
        }
        return ResponseEntity.ok("Successfull");
    }

    @GetMapping("/book_list/find")
    public String findBooks(
            @RequestParam(required = false) String bookName,
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) String genre, Model model
    ) {
        if (bookName != null) {
            model.addAttribute("books", bookService.findBooksByName(bookName));
        } else if (authorName != null) {
            model.addAttribute("books", bookService.findBooksByAuthorName(authorName));
        } else if (genre != null) {
            model.addAttribute("books", bookService.findBooksByGenre(Genre.valueOf(genre)));
            model.addAttribute("selectedGenre", genre);
        }
        model.addAttribute("genres", bookService.getAllGenre());
        return "book_list";
    }


    //Endpoint để xóa cột
//    @PostMapping("/drop-column")
//    public String dropColumn() {
//        bookService.dropColumn();
//        return "Column dropped successfully";
//    }


}
