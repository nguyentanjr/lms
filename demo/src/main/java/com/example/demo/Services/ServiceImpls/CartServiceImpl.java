package com.example.demo.Services.ServiceImpls;

import com.example.demo.DTO.BookDTO;
import com.example.demo.Model.Book;
import com.example.demo.Model.Cart;
import com.example.demo.Services.Service.BookService;
import com.example.demo.Services.Service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private BookService bookService;
    @Autowired
    private ModelMapper modelMapper;

    public String addBookToCart(long bookId, @ModelAttribute("cart") Cart cart) {
        if (checkBookHadAlreadyInCart(bookId,cart)) {
            return "You have already added this book to the cart!";
        } else {
            Book book = bookService.findBookByBookId(bookId);
            BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
            cart.addBookToCart(bookDTO);
            return "Successfully added book to cart!";
        }
    }
    public boolean checkBookHadAlreadyInCart(long bookId,@ModelAttribute("cart") Cart cart) {
        Book book = bookService.findBookByBookId(bookId);
        BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
        return cart.getBookList().contains(bookDTO);
    }
    public void removeBookInCart(long bookId,@ModelAttribute("cart")Cart cart) {
        Book book = bookService.findBookByBookId(bookId);
        BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
        cart.getBookList().remove(bookDTO);
    }
}
