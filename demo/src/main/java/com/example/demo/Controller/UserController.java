package com.example.demo.Controller;

import com.example.demo.Model.Book;
import com.example.demo.Model.User;
import com.example.demo.Respository.UserRepository;
import com.example.demo.Service.BookService;
import com.example.demo.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @GetMapping("/register")
    public String register(Authentication authentication) {
        if (authentication != null) {
            return "redirect:/admin_dashboard";
        }
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user,Model model) {
        Optional<User> myUser = userService.findUserByUserName(user.getUsername());
        if(myUser.isPresent()) {
            model.addAttribute("existingUser","the username has existed");
            return "register";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String login(Authentication authentication) {
        if(authentication != null) {
            return "redirect:/home";
        }
        return "/login";
    }
    @GetMapping("/home")
    public String userDashboard() {
        return "user_dashboard";
    }

    @GetMapping("/book_list/borrow/{book_id}")
    public String borrowBook(@PathVariable long book_id) {
        Book book = bookService.findBookById(book_id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(authentication.getName()).get();
        if(book.getCopiesAvailable() > 0) {
            book.setCopiesAvailable(book.getCopiesAvailable() - 1);
            user.getBooks().add(book);
            book.getUsers().add(user);
            userService.saveUser(user);
            bookService.saveBook(book);
        }
        return "redirect:/book_list";
    }

}
