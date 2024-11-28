package com.example.demo.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="book_id")
    private long id;

    private String title;

    @Column(name="hidden")
    private boolean hidden;

    @Column(name="published_date")
    private String publishedDate;

    @Column(name="copies_available")
    private int copiesAvailable;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    List<UserBook> userBooks = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    List<BookReservation> bookReservations = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    List<BorrowedHistory> borrowedHistories = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "book_categories", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name="categories")
    private List<String> categories;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="book_authors", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "author_name")
    private List<String> authors;
    public String getStatus() {
        return copiesAvailable > 0 ? "Available" : "Checked Out";
    }


}
