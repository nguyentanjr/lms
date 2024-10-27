package com.example.demo.Controller;

import com.example.demo.Model.Book;
import com.example.demo.Model.Student;
import com.example.demo.Model.User;
import com.example.demo.Respository.UserRepository;
import com.example.demo.Service.BookService;
import com.example.demo.Service.StudentService;
import com.example.demo.Service.UserService;
import org.h2.engine.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
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

    @GetMapping("/book_list")
    public String getBookList(Model model) {
        model.addAttribute("books",bookService.getAllBooks());
        return "book_list";
    }

    @GetMapping("/book_list/{id}")
    public String borrowBook(@PathVariable long id, RedirectAttributes redirectAttributes) {
        Book book = bookService.findBookById(id);
        User user = userService.findUserById(id);
        if (book.isAvailable()) {
            user.getBooks().add(book);
            book.setCopiesAvailable(book.getCopiesAvailable() - 1);
            userService.saveUser(user);
            bookService.saveBook(book);
            redirectAttributes.addFlashAttribute("message", "Book borrowed successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "User or Book not found, or book is not available!");
        }
        return "redirect:/book_list";
    }
}
