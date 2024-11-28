package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class BorrowedHistory {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private long bookID;
    private String title;
    private String publishedDate;
    private LocalDate dateBorrowed;
    private LocalDate dueDate;
    private LocalDate dateReturn;
    private String returnStatus;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonIgnore
    private Book book;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "borrowed_history_categories",
            joinColumns = @JoinColumn(name = "borrowed_history_id"))
    @Column(name = "category")
    private List<String> bookCategories = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "borrowed_history_authors",
            joinColumns = @JoinColumn(name = "borrowed_history_id"))
    @Column(name = "author_name")
    private List<String> bookAuthors = new ArrayList<>();

    public void copyBookInfo(Book book) {
        this.book = book;
        this.bookCategories = new ArrayList<>(book.getCategories());
        this.bookAuthors = new ArrayList<>(book.getAuthors());
    }


}
