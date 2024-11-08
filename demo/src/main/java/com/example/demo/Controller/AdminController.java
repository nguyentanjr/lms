package com.example.demo.Controller;

import com.example.demo.Services.Service.UserService;
import com.example.demo.Services.ServiceImpls.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminController {
    @Autowired
    UserService userService;

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        int totalUsers = userService.countTotalUsers();
        model.addAttribute("totalUsers", totalUsers);
        return "admin_dashboard";
    }

}
