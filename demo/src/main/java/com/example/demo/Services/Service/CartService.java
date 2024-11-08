package com.example.demo.Services.Service;

import com.example.demo.Model.Cart;
import com.example.demo.Model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface CartService {
    String addBookToCart(long bookId, @ModelAttribute("cart")Cart cart);
    boolean checkBookInCart(long bookId, @ModelAttribute("cart")Cart cart);
    void removeBookInCart(long bookId,@ModelAttribute("cart")Cart cart);
}
