package com.example.demo.Controller;

import com.example.demo.Model.User;
import com.example.demo.Services.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


@Controller
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
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
        userService.saveUser(user);
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



}
