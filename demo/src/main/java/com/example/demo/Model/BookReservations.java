package com.example.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class BookReservations {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @CreationTimestamp
    private LocalDate reservationDate;

    private LocalDate borrowedDate;

    private LocalDate returnDate;

    private String status;

}
