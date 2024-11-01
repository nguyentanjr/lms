package com.example.demo.Model;
import com.example.demo.Model.Enum.Genre;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="book_id")
    private long id;

    private String name;

    @Column(name="status")
    private boolean isAvailable;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private int publishedYear;

    private int copiesAvailable;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonIgnore
    Author author;

    @OneToMany(mappedBy = "book")
    List<UserBook> userBooks = new ArrayList<>();

    public String getStatus() {
        return copiesAvailable > 0 ? "Available" : "Checked Out";
    }



}
