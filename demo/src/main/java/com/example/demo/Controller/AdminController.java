package com.example.demo.Controller;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Services.Service.UserService;
import com.example.demo.Services.ServiceImpls.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        int totalUsers = userService.countTotalUsers();
        model.addAttribute("totalUsers", totalUsers);
        return "admin_dashboard";
    }
    @GetMapping("/user_list")
    public String userList(Model model) {
        model.addAttribute("users",userService.getAllUsers());
        return "user_list";
    }

    @GetMapping("/remove-user")
    public ResponseEntity<String> removeUser(long userId) {
        userService.removeUser(userId);
        System.out.println(userId);
        return ResponseEntity.ok("User removed successfully");
    }

}
