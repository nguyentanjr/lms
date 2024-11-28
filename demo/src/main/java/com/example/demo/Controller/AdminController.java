package com.example.demo.Controller;

import com.example.demo.DTO.ShowBooksBorrowedByUserDTO;
import com.example.demo.DTO.UpdateUserDTO;
import com.example.demo.Model.User;
import com.example.demo.Model.UserBook;
import com.example.demo.Services.Service.BookService;
import com.example.demo.Services.Service.UserBookService;
import com.example.demo.Services.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SessionRegistry sessionRegistry;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserBookService userBookService;
    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        int totalUsers = userService.countTotalUsers();
        model.addAttribute("totalUsers", totalUsers);
        return "admin_dashboard";
    }
    @GetMapping("/admin/user_list")
    public String userList(Model model) {
        model.addAttribute("users",userService.getAllUsers());
        return "admin_user_list";
    }

    @GetMapping("/remove-user")
    public ResponseEntity<Boolean> removeUser(long userId) {
        if(userService.validateUserDeletion(userId)) {
            userBookService.deleteRelationByUserId(userId);
            userService.removeUser(userId);
            return ResponseEntity.ok(true);
        }
        else {
            return  ResponseEntity.ok(false);
        }
    }
    @PostMapping("/update-user")
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserDTO updateUserDTO) {
        User user = userService.findUserByUserName(updateUserDTO.getUsername()).get();
        System.out.println(updateUserDTO.getId());
        user.setFullName(updateUserDTO.getFullName());
        user.setEmail(updateUserDTO.getEmail());
        user.setPhoneNumber(updateUserDTO.getPhoneNumber());
        user.setGender(updateUserDTO.getGender());
        userService.saveUser(user);
        return ResponseEntity.ok("Success update user!");
    }

    @GetMapping("/admin/book_list")
    public String bookList(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "admin_book_list";
    }
    @GetMapping("/admin/book_list/find")
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
        return "admin_book_list";
    }
    @GetMapping("/admin/user_list/find")
    public String findBooks(@RequestParam(required = false) String username, Model model) {
        User user = userService.findUserByUserName(username).get();
        List<User> users = new ArrayList<>();
        users.add(user);
        model.addAttribute("users", users);
        return "admin_user_list";
    }

}
