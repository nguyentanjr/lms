package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  // Chỉ định HomeController là Controller
public class UserAuthentication {
    @GetMapping("/register")
    public String userRegister() {
        return "register";
    }
    @GetMapping("/login")
    public String userLogin() {
        return "login";
    }
}