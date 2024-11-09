package com.example.demo.Model;
import com.example.demo.Model.Enum.Genre;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.sql.Date;
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

    private String title;


    @Column(name="published_date")
    private String publishedDate;

    @Column(name="copies_available")
    private int copiesAvailable;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    List<UserBook> userBooks = new ArrayList<>();

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
