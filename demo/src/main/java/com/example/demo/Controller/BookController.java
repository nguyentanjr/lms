package com.example.demo.Controller;

import com.example.demo.DTO.AddBookDTO;
import com.example.demo.DTO.BookDTO;
import com.example.demo.DTO.ShowBooksBorrowedByUserDTO;
import com.example.demo.Model.Book;
import com.example.demo.Model.Cart;
import com.example.demo.Model.Enum.Genre;
import com.example.demo.Model.User;
import com.example.demo.Model.UserBook;
import com.example.demo.Respository.UserBookRepository;
import com.example.demo.Services.Service.BookService;
import com.example.demo.Services.Service.UserBookService;
import com.example.demo.Services.Service.UserService;
import com.example.demo.Services.ServiceImpls.UserBookServiceImpl;
import com.example.demo.Services.ServiceImpls.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Controller
@SessionAttributes("bookList")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserBookService userBookService;

    @GetMapping("/book_list")
    public String getAllBooks(Model model, @ModelAttribute("cart") Cart cart) {
        model.addAttribute("books", bookService.getAllBooks());
        return "book_list";
    }


    @GetMapping("/book_list/get_copies/{bookId}")
    public ResponseEntity<Integer> getBookCopies(@PathVariable long bookId) {
        int copiesAvailable = bookService.getBookCopies(bookId);
        return ResponseEntity.ok(copiesAvailable);
    }

    @GetMapping("/book_list/borrowed_check/{bookId}")
    public ResponseEntity<Boolean> checkUserHasBorrowedBook(@PathVariable long bookId) {
        return ResponseEntity.ok(bookService.checkUserHasBorrowedBook(bookId));
    }

    @GetMapping("/book_list/set_copies")
    public ResponseEntity<String> setCopies(long bookId, int copies) {
        Book book = bookService.findBookByBookId(bookId);
        bookService.saveBookBorrowedByUser(bookId,copies);
        return ResponseEntity.ok("Successfully");
    }


    @GetMapping("/book_list/find")
    public String findBooks(
            @RequestParam String searchValue,
            @RequestParam String filterType, Model model) {
       if(filterType.equals("title")) {
           model.addAttribute("books",bookService.findBooksByTitle(searchValue));
       }
       else if(filterType.equals("id")) {
           long bookId = Long.parseLong(searchValue);
           model.addAttribute("books",bookService.findBookByBookId(bookId));
       }
        if(filterType.equals("author")) {
            model.addAttribute("books",bookService.findBookByAuthor(searchValue));
        }
        if(filterType.equals("category")) {
            model.addAttribute("books",bookService.findBookByCategory(searchValue));
        }
        return "book_list";
    }



    @GetMapping("/remove-book")
    public ResponseEntity<String> removeBook(long bookId) {
        userBookService.deleteRelationByBookId(bookId);
        bookService.removeBookById(bookId);
        bookService.saveBooksToJson();
        return ResponseEntity.ok("Book removed successfully");
    }

    @GetMapping("/set-hide-book")
    public ResponseEntity<String> setHideBook(String status, long bookId) {
        Book book = bookService.findBookByBookId(bookId);
        if (status.equals("hide")) {
            book.setHidden(true);
        } else {
            book.setHidden(false);
        }
        bookService.saveBook(book);
        return ResponseEntity.ok("Book removed successfully");
    }

    @GetMapping("/check-hide-book")
    public ResponseEntity<Boolean> checkHideBook(long bookId) {
        Book book = bookService.findBookByBookId(bookId);
        return ResponseEntity.ok(book.isHidden());
    }

    @PostMapping("/edit-book")
    @ResponseBody
    public ResponseEntity<String> editBook(@RequestBody Book book) {
        Book myBook = bookService.findBookByBookId(book.getId());
        if (!book.getTitle().isEmpty()) {
            myBook.setTitle(book.getTitle());
        }
        if (book.getAuthors() != null) {
            myBook.setAuthors(book.getAuthors());
        }
        if (book.getCategories() != null) {
            myBook.setCategories(book.getCategories());
        }
        if (!book.getPublishedDate().isEmpty()) {
            myBook.setPublishedDate(book.getPublishedDate());
        }
        bookService.saveBook(myBook);
        return ResponseEntity.ok("Successful");
    }

    @GetMapping("/suggest-book")
    public ResponseEntity<List<String>> displaySuggestBooks(String query) {
        return ResponseEntity.ok(bookService.getBookSuggestion(query));
    }

    @PostMapping("/add-book")
    @ResponseBody
    public ResponseEntity<String> addABook(@RequestBody AddBookDTO addBookDTO) {
        bookService.addBook(addBookDTO);
        return ResponseEntity.ok("Add book sucessfully!");
    }
    @GetMapping("/show-books-user-borrowed-for-admin")
    @ResponseBody
    public List<ShowBooksBorrowedByUserDTO> showBooksUserBorrowedForAdmin(long userId, Model model) {
        model.addAttribute("currentDate", LocalDate.now());
        return userBookService.getBooksWithBasicInfoForAdmin(userId);
    }
    @GetMapping("/show-books-user-borrowed-for-user")
    @ResponseBody
    public List<ShowBooksBorrowedByUserDTO> showBooksUserBorrowedForUser(long userId, Model model) {
        model.addAttribute("currentDate", LocalDate.now());
        List<ShowBooksBorrowedByUserDTO> showBooksBorrowedByUserDTOS = userBookService.getBooksWithDetailedInfoForUser(userId);
        Collections.reverse(showBooksBorrowedByUserDTOS);
        return showBooksBorrowedByUserDTOS;
    }

    @PostMapping("/return-book")
    public ResponseEntity<String> returnBook(long bookId) {
        bookService.returnBook(bookId);
        return ResponseEntity.ok("Book with ID " + bookId + " returned successfully.");
    }
}
