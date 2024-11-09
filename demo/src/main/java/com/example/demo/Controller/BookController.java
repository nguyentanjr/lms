package com.example.demo.Controller;

import com.example.demo.DTO.BookDTO;
import com.example.demo.Model.Book;
import com.example.demo.Model.Cart;
import com.example.demo.Model.Enum.Genre;
import com.example.demo.Model.User;
import com.example.demo.Model.UserBook;
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

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("bookList")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserBookService userBookService;
    @Autowired
    private ModelMapper modelMapper;

    @ModelAttribute("bookList")
    public List<Book> bookList() {
        return new ArrayList<>();
    }

    @GetMapping("/book_list")
    public String getAllBooks(Model model,@ModelAttribute("cart")Cart cart) {
        model.addAttribute("books", bookService.getAllBooks());
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

    @GetMapping("/book_list/borrow")
    public ResponseEntity<String> borrowBook(long bookId, int copies, @ModelAttribute List<BookDTO> cartList) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(authentication.getName()).get();
        Book book = bookService.findBookByBookId(bookId);
        if (userBookService.hasUserBorrowedBook(user.getId(), book.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("You had already borrow this book!");
        }
        if (book.getCopiesAvailable() > 0) {
            book.setCopiesAvailable(copies);
            userBookService.save(new UserBook(user, book));
        }
        return ResponseEntity.ok("Successfull");
    }


    @GetMapping("/book_list/find")
    public String findBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String authorName, Model model
    ) {
        if (title != null) {
            model.addAttribute("books", bookService.findBooksByTitle(title));
        } else if (authorName != null) {
            model.addAttribute("books", bookService.findBookByAuthor(authorName));
        }
        return "book_list";
    }
    @PostMapping("/cart-borrow")
    public ResponseEntity<String> borrowedFromCart(@RequestBody List<BookDTO> bookDTOList,
                                                   @ModelAttribute("cart") Cart cart, HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(authentication.getName()).get();
        List<UserBook> userBooks = new ArrayList<>();
        for(BookDTO bookDTO : bookDTOList) {
            Book book = modelMapper.map(bookDTO,Book.class);
            userBooks.add(new UserBook(user, book));
            cart.getBookList().clear();
            session.setAttribute("cart", cart);
        }
        userBookService.saveAll(userBooks);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/remove-book")
    public ResponseEntity<String> removeBook(long bookId) {
        userBookService.unassignBookFromUsers(bookId);
        bookService.removeBookById(bookId);
        return ResponseEntity.ok("Book removed successfully");
    }

    @GetMapping("/set-hide-book")
    public ResponseEntity<String> setHideBook(String status,long bookId) {
        Book book = bookService.findBookByBookId(bookId);
        if(status.equals("hide")) {
            book.setHidden(true);
        }
        else {
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

}
