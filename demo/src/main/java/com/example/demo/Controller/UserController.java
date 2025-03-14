package com.example.demo.Controller;

import com.example.demo.DTO.UpdateInformationDTO;
import com.example.demo.Model.User;
import com.example.demo.Services.Service.UserBookService;
import com.example.demo.Services.Service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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

import java.util.Optional;


@Controller
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserBookService userBookService;

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
            model.addAttribute("existingUser", "The username has already been taken.");
            return "register";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        userService.saveUser(user);
        return "redirect:/login";
    }


    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null) {
            return "redirect:/home";
        }
        return "login";
    }

    @GetMapping("/home")
    public String userDashboard(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User user = userService.findUserByUserName(username).get();
        if (user.getEmail() == null) {
            return "redirect:/update_information";
        } else {
            return "index";
        }
    }

    @GetMapping("/update_information")
    public String updateInformation() {
        return "update_information";
    }

    @PostMapping("/update_information")
    public ResponseEntity<String> updateInformationSuccess(@ModelAttribute UpdateInformationDTO updateInformationDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User user = userService.findUserByUserName(username).get();

        user.setEmail(updateInformationDTO.getEmail());
        user.setFullName(updateInformationDTO.getFullName());
        user.setPhoneNumber(updateInformationDTO.getPhoneNumber());
        user.setGender(updateInformationDTO.getGender());
        userService.saveUser(user);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/get_user_id")
    public ResponseEntity<Long> getUserId() {
        Long userId = userService.getUserId();
        return ResponseEntity.ok(userId);
    }
    @GetMapping("/isUser")
    public ResponseEntity<Boolean> isUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return ResponseEntity.ok(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @GetMapping("/profile")
    public String profile() {
        return "change_information";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/getTokenFromCookie")
    public String getTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
            }
        }
         return "book_list";
    }

    @GetMapping("/count-users-online")
    public ResponseEntity<int[]> countUsersOnline() {
        return ResponseEntity.ok(userService.countUsersOnlineAndNewRegister());
    }

}
