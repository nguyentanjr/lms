package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "user_book")
public class UserBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonIgnore
    private Book book;

    public UserBook() {}
    public UserBook(User user,Book book){
        this.user = user;
        this.book = book;
    };


    @CreationTimestamp
    @Column(name = "borrow_date")
    private LocalDate borrowDate;

    @UpdateTimestamp
    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "due_date")
    private LocalDate dueDate;


}
