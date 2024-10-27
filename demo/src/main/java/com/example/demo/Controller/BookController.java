package com.example.demo.Controller;

import com.example.demo.DTO.BorrowRequest;
import com.example.demo.Model.Book;
import com.example.demo.Model.Enum.Genre;
import com.example.demo.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/book_list")
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("genres", bookService.getAllGenre());
        return "book_list";
    }

    @GetMapping("/book_list/find")
    public String findBooks(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) String genre, Model model
    ) {
        if(name != null) {
            model.addAttribute("books",bookService.findBooksByName(name));
        }
        else if(authorName != null) {
            model.addAttribute("books",bookService.findBooksByAuthorName(authorName));
        }
        else if(genre != null) {
            model.addAttribute("books",bookService.findBooksByGenre(Genre.valueOf(genre)));
        }
        model.addAttribute("genres", bookService.getAllGenre());
        return "book_list";
    }
    //Endpoint để xóa cột
//    @PostMapping("/drop-column")
//    public String dropColumn() {
//        bookService.dropColumn();
//        return "Column dropped successfully";
//    }


}
