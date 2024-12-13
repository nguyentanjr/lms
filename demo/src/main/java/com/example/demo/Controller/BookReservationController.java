package com.example.demo.Controller;

import com.example.demo.DTO.BookReservationDTO;
import com.example.demo.Services.Service.BookReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.List;

@Controller
public class BookReservationController {
    @Autowired
    private BookReservationService bookReservationService;

    @PostMapping("/reserve-book")
    public ResponseEntity<String> reserveBook(long bookId) {
        bookReservationService.reserveBook(bookId);
        return ResponseEntity.ok("Book with ID " + bookId + " reserved successfully.");
    }

    @GetMapping("/check-user-has-reserve-book")
    public ResponseEntity<String> checkReserveBook(long bookId) {
        if(bookReservationService.checkUserHasReserveBook(bookId)) {
            return ResponseEntity.ok("Duplicate");
        }
        else return ResponseEntity.ok("Not duplicate");
    }

    @GetMapping("/show-reserve-book")
    public ResponseEntity<List<BookReservationDTO>> showReserveBook() {
        List<BookReservationDTO> bookReservationDTO = bookReservationService.getReserveBook();
        Collections.reverse(bookReservationDTO);
        return ResponseEntity.ok(bookReservationDTO);
    }
    @GetMapping("/remove-book-in-reserve-list")
    public ResponseEntity<String> removeBook(long bookId) {
        bookReservationService.deleteBookInReserveList(bookId);
        return ResponseEntity.ok("Delete Successfully");
    }

    @GetMapping("/process-reservation")
    public ResponseEntity<String> processReservation() {
        bookReservationService.processReservation();
        return ResponseEntity.ok("Successfully");
    }
}
