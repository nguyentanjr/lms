package com.example.demo.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookReservationDTO {
    private long id;
    private String title;
    private LocalDate reservationDate;
    private String status;

    public BookReservationDTO(long id,String title,LocalDate reservationDate,String status) {
        this.id = id;
        this.title = title;
        this.reservationDate = reservationDate;
        this.status = status;
    }
}
