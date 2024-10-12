package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Author {
    @Id
    private String name;
    private int yearOfPublication;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Book> bookWritten;
}
