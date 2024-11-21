package com.example.demo.DTO;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ShowBooksBorrowedByUserDTO {
    private long id;
    private String title;
    private LocalDate dateBorrowed;
    private LocalDate dueDate;
    private String publishedDate;
    private List<String> categories;
    private List<String> authors;

    public ShowBooksBorrowedByUserDTO(long id,String title,LocalDate dateBorrowed,LocalDate dueDate) {
        this.id = id;
        this.title = title;
        this.dateBorrowed = dateBorrowed;
        this.dueDate = dueDate;
    }
    public ShowBooksBorrowedByUserDTO(long id,String title,LocalDate dateBorrowed,LocalDate dueDate,
                                      String publishedDate,List<String> categories,List<String> authors) {
        this.id = id;
        this.title = title;
        this.dateBorrowed = dateBorrowed;
        this.dueDate = dueDate;
        this.publishedDate = publishedDate;
        this.categories = categories;
        this.authors = authors;
    }
}
