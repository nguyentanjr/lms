package com.example.demo.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;

    private String name;

    private boolean isAvailable;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private int publishedYear;

    private int copiesAvailable;



    @ManyToOne
    @JoinColumn(name = "author_name")
    @JsonIgnore
    Author author;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Transaction> transactionList;



}
