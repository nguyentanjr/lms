package com.example.demo.Services.ServiceImpls;

import com.example.demo.Controller.NotificationController;
import com.example.demo.DTO.BookReservationDTO;
import com.example.demo.Model.*;
import com.example.demo.Respository.BookReservationRepository;
import com.example.demo.Respository.NotificationRepository;
import com.example.demo.Services.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private     NotificationController notificationController;
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

    @Transactional
    public void processReservation() {
        List<BookReservation> bookReservations = bookReservationRepository.findAll();
        for(BookReservation bookReservation : bookReservations) {
            long bookId = bookReservation.getBook().getId();
            Book book = bookService.findBookByBookId(bookId);
            if(book.getCopiesAvailable() > 0 && bookReservation.getStatus().equals("Pending")) {
                long userId = bookReservation.getUser().getId();
                String username = userService.findUserNameByUserId(userId);
                int newCopiesAvailable = book.getCopiesAvailable() - 1;
                book.setCopiesAvailable(newCopiesAvailable);
                bookService.saveBookBorrowedByUser(bookId, userId, newCopiesAvailable);
                String message = "ID " + bookId + ": Your reservation have been approved!";
                notificationService.sendNotificationToUser(username, message);
                bookReservation.setStatus("Approved");
                save(bookReservation);
            }
        }
    }
    public List<BookReservation> getAll() {
        return bookReservationRepository.findAll();
    }
    public void deleteRelationByBookId(long bookId) {
        bookReservationRepository.deleteByBookId(bookId);
    }
}
