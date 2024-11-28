package com.example.demo.Controller;

import com.example.demo.Model.Book;
import com.example.demo.Model.BorrowedHistory;
import com.example.demo.Services.Service.BorrowedHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;

@Controller
public class BorrowedHistoryController {
    @Autowired
    private BorrowedHistoryService borrowedHistoryService;
    @GetMapping("/save-history-borrowed")
    public ResponseEntity<String> saveHistoryBorrowed(long bookId) {
        borrowedHistoryService.addHistoryBorrowedBooks(bookId);
        return ResponseEntity.ok("Successfully");
    }

    @GetMapping("/show-borrowed-history")
    public ResponseEntity<List<BorrowedHistory>> showBorrowedHistory() {
        List<BorrowedHistory> borrowedHistory = borrowedHistoryService.showBorrowedHistory();
        Collections.reverse(borrowedHistory);
        return ResponseEntity.ok(borrowedHistory);
    }
}
