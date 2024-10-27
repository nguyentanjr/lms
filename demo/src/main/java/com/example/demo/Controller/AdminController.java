package com.example.demo.Controller;

import com.example.demo.Model.User;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/admin/user_list")
    public String getUserList(Model model) {
        model.addAttribute("users",userService.getAllUsers());
        return "user_list";
    }

    @GetMapping("/admin/user_list/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.deleteUserByUserId(id);
        return "redirect:/admin/user_list";
    }

    @GetMapping("/admin/user_list/book_borrowed/{id}")
    public String bookBorrowed(@PathVariable long id,Model model) {
        model.addAttribute("user",userService.findUserById(id));
        return "book_borrowed";
    }
}
