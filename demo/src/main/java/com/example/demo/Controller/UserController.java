package com.example.demo.Controller;

import com.example.demo.DTO.UpdateInformationDTO;
import com.example.demo.Model.Book;
import com.example.demo.Model.User;
import com.example.demo.Services.Service.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;


@Controller
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/register")
    public String register(Authentication authentication) {
        if (authentication != null) {
            return "redirect:/admin_dashboard";
        }
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        Optional<User> myUser = userService.findUserByUserName(user.getUsername());
        if (myUser.isPresent()) {
            model.addAttribute("existingUser", "the username has existed");
            return "register";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null) {
            return "redirect:/home";
        }
        return "/login";
    }

    @GetMapping("/home")
    public String userDashboard(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User user = userService.findUserByUserName(username).get();
        if (user.getEmail() == null) {
            return "update_information";
        } else {
            return "user_dashboard";
        }
    }

    @PostMapping("/updateInfo")
    public ResponseEntity<String> updateInformation(@ModelAttribute UpdateInformationDTO updateInformationDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        Object principal = authentication.getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User user = userService.findUserByUserName(username).get();
        user.setEmail(updateInformationDTO.getEmail());
        user.setFullName(updateInformationDTO.getFullName());
        user.setPhoneNumber(updateInformationDTO.getPhoneNumber());
        user.setGender(updateInformationDTO.getGender());
        userService.saveUser(user);
        return ResponseEntity.ok("Update Information Successfully!");
    }
    @GetMapping("/isUser")
    public ResponseEntity<Boolean> isUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return ResponseEntity.ok(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
