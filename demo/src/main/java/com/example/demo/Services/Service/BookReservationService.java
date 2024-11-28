package com.example.demo.Services.Service;

import com.example.demo.DTO.BookReservationDTO;
import com.example.demo.Model.BookReservation;

import java.util.List;

public interface BookReservationService {
    void save(BookReservation bookReservation);
    void reserveBook(long bookId);
    List<BookReservationDTO> getReserveBook();

    Boolean checkUserHasReserveBook(long bookId);

    void deleteBookInReserveList(long bookId);
    void processReservation();
}