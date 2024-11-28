package com.example.demo.Services.ServiceImpls;

import com.example.demo.Model.Book;
import com.example.demo.Model.BorrowedHistory;
import com.example.demo.Model.User;
import com.example.demo.Model.UserBook;
import com.example.demo.Respository.BorrowedHistoryRepository;
import com.example.demo.Respository.UserBookRepository;
import com.example.demo.Services.Service.BookService;
import com.example.demo.Services.Service.BorrowedHistoryService;
import com.example.demo.Services.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowedHistoryServiceImpl implements BorrowedHistoryService {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserBookRepository userBookRepository;
    @Autowired
    private BorrowedHistoryRepository borrowedHistoryRepository;
    public void addHistoryBorrowedBooks(long bookId) {
        long userId = userService.getUserId();
        User user = userService.findUserById(userId);
        Book book = bookService.findBookByBookId(bookId);
        UserBook userBook = userBookRepository.findByBookIdAndUserId(bookId,userId);
        LocalDate returnDate = LocalDate.now();
        BorrowedHistory borrowedHistory = new BorrowedHistory();
        borrowedHistory.setBook(book);
        borrowedHistory.setUser(user);
        borrowedHistory.setTitle(book.getTitle());
        borrowedHistory.setPublishedDate(book.getPublishedDate());
        borrowedHistory.setBookID(bookId);
        borrowedHistory.setDateBorrowed(userBook.getBorrowDate());
        borrowedHistory.setDueDate(userBook.getDueDate());
        borrowedHistory.setDateReturn(returnDate);
        borrowedHistory.setReturnStatus(returnDate.isBefore(borrowedHistory.getDueDate()) ? "On Time" : "Late");
        borrowedHistory.copyBookInfo(book);
        borrowedHistoryRepository.save(borrowedHistory);
    }

    public List<BorrowedHistory> showBorrowedHistory() {
        long userId = userService.getUserId();
        return borrowedHistoryRepository.findByUserId(userId);
    }
    public void save(BorrowedHistory borrowedHistory) {
         borrowedHistoryRepository.save(borrowedHistory);
    }


}
