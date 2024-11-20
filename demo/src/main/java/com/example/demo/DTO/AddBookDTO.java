package com.example.demo.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddBookDTO {
    private String title;
    private List<String> categories;
    private List<String> authors;
    private String publishedDate;
    public int copies_available;
}
