package com.example.demo.Services.ServiceImpls;

import com.example.demo.DTO.BookReservationDTO;
import com.example.demo.Model.Book;
import com.example.demo.Model.BookReservation;
import com.example.demo.Model.User;
import com.example.demo.Model.UserBook;
import com.example.demo.Respository.BookReservationRepository;
import com.example.demo.Services.Service.BookReservationService;
import com.example.demo.Services.Service.BookService;
import com.example.demo.Services.Service.UserBookService;
import com.example.demo.Services.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookReservationServiceImpl implements BookReservationService {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookReservationRepository bookReservationRepository;
    @Autowired
    private UserBookService userBookService;
    public void save(BookReservation bookReservation) {
        bookReservationRepository.save(bookReservation);
    }
    public void reserveBook(long bookId) {
        if(!checkUserHasReserveBook(bookId)) {
            long userId = userService.getUserId();
            User user = userService.findUserById(userId);
            Book book = bookService.findBookByBookId(bookId);
            BookReservation bookReservation = new BookReservation();
            LocalDate reserveDate = LocalDate.now();
            bookReservation.setBook(book);
            bookReservation.setUser(user);
            bookReservation.setReservationDate(reserveDate);
            bookReservation.setStatus("Pending");
            System.out.println(bookId);
            save(bookReservation);
        }
    }

    public List<BookReservationDTO> getReserveBook() {
        long userId = userService.getUserId();
        return bookReservationRepository.getReservationBook(userId);
    }

    public Boolean checkUserHasReserveBook(long bookId) {
        long userId = userService.getUserId();
        return bookReservationRepository.checkUserHasReserveBook(bookId, userId) > 0;
    }

    public void deleteBookInReserveList(long bookId) {
        bookReservationRepository.deleteBookReservationById(bookId);
    }

    public void processReservation() {
        List<BookReservation> bookReservations = bookReservationRepository.findAll();
        for(BookReservation bookReservation : bookReservations) {
            long bookId = bookReservation.getBook().getId();
            Book book = bookService.findBookByBookId(bookId);
            if(book.getCopiesAvailable() > 0 && bookReservation.getStatus().equals("Pending")) {
                bookService.saveBookBorrowedByUser(bookId,book.getCopiesAvailable() - 1);
                bookReservation.setStatus("Approved");
                save(bookReservation);
            }
        }
    }
}
