package com.example.demo.Controller;

import com.example.demo.DTO.BookDTO;
import com.example.demo.Model.Book;
import com.example.demo.Model.Cart;
import com.example.demo.Model.User;
import com.example.demo.Services.Service.BookService;
import com.example.demo.Services.Service.CartService;
import com.example.demo.Services.Service.UserBookService;
import com.example.demo.Services.Service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.beans.Transient;
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

    @ModelAttribute("cart")
    public Cart createCart() {
        return new Cart();
    }

    @GetMapping("/add_to_cart")
    public ResponseEntity<String> addToCart(@ModelAttribute("cart") Cart cart, long bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(authentication.getName()).get();
        if(userBookService.hasUserBorrowedBook(user.getId(),bookId)) {
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
        if(cartService.checkBookInCart(bookId,cart)) {
            return ResponseEntity.ok("in cart");
        }
        if(userBookService.hasUserBorrowedBook(user.getId(),bookId)) {
            return ResponseEntity.ok("has borrowed");
        }
        return ResponseEntity.ok("not in cart");

    }

    @GetMapping("/remove-book-in-cart")
    public ResponseEntity<List<BookDTO>> removeBook(long bookId, @ModelAttribute("cart") Cart cart, HttpSession session) {
        cartService.removeBookInCart(bookId,cart);
        return ResponseEntity.ok(cart.getBookList());
    }
}
