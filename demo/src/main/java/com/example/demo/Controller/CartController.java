package com.example.demo.Controller;

import com.example.demo.DTO.BookDTO;
import com.example.demo.Model.Book;
import com.example.demo.Model.Cart;
import com.example.demo.Model.User;
import com.example.demo.Model.UserBook;
import com.example.demo.Services.Service.BookService;
import com.example.demo.Services.Service.CartService;
import com.example.demo.Services.Service.UserBookService;
import com.example.demo.Services.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("cart")
public class CartController {
    @Autowired
    private BookService bookService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserBookService userBookService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;


    @ModelAttribute("cart")
    public Cart createCart() {
        return new Cart();
    }

    @GetMapping("/add_to_cart")
    public ResponseEntity<String> addToCart(@ModelAttribute("cart") Cart cart, long bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(authentication.getName()).get();
        if(bookService.checkUserHasBorrowedBook(bookId)) {
            return ResponseEntity.ok("You had already borrow");
        }
        return ResponseEntity.ok(cartService.addBookToCart(bookId,cart));
    }

    @GetMapping("/cart_list")
    @ResponseBody
    public List<BookDTO> showCart(@ModelAttribute("cart") Cart cart) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(authentication.getName()).get();
        cart.getBookList().removeIf(bookDTO -> userBookService.hasUserBorrowedBook(user.getId(), bookDTO.getId()));
        return cart.getBookList();
    }
    @GetMapping("/check-book-in-cart")
    public ResponseEntity<String> checkInCart(long bookId,@ModelAttribute("cart") Cart cart) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(authentication.getName()).get();
        if(cartService.checkBookHadAlreadyInCart(bookId,cart)) {
            return ResponseEntity.ok("in cart");
        }
        if(userBookService.hasUserBorrowedBook(user.getId(),bookId)) {
            return ResponseEntity.ok("has borrowed");
        }
        return ResponseEntity.ok("not in cart");

    }

    @PostMapping("/cart-borrow")
    public ResponseEntity<String> borrowedFromCart(@RequestBody List<BookDTO> bookDTOList,
                                                   @ModelAttribute("cart") Cart cart, HttpSession session) {
        User user = userService.findUserByUserName(userService.getUsername()).get();
        List<UserBook> userBooks = new ArrayList<>();
        for (BookDTO bookDTO : bookDTOList) {
            Book book = modelMapper.map(bookDTO, Book.class);
            UserBook userBook = new UserBook();
            userBook.setBook(book);
            userBook.setUser(user);
            LocalDate borrowDate = LocalDate.now();
            LocalDate dueDate = borrowDate.plusDays(60);
            userBook.setDueDate(dueDate);
            userBooks.add(userBook);
            cart.getBookList().clear();
            bookService.saveBook(book);
            session.setAttribute("cart", cart);
        }
        userBookService.saveAll(userBooks);
        return ResponseEntity.ok("Success");
    }
    @GetMapping("/remove-book-in-cart")
    public ResponseEntity<List<BookDTO>> removeBook(long bookId, @ModelAttribute("cart") Cart cart, HttpSession session) {
        cartService.removeBookInCart(bookId,cart);
        return ResponseEntity.ok(cart.getBookList());
    }
}
