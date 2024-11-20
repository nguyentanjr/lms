package com.example.demo.Model;

import com.example.demo.DTO.BookDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Cart {
    private long cartId;
    private List<BookDTO> bookList;
    public Cart() {
        this.bookList = new ArrayList<>();
    }

    public void addBookToCart(BookDTO bookDTO) {
        this.bookList.add(bookDTO);
    }

    public List<BookDTO> cartList() {
        return this.bookList;
    }

}
