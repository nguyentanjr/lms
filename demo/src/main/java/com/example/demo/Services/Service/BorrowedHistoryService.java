package com.example.demo.Services.Service;

import com.example.demo.DTO.BorrowedHistoryDTO;
import com.example.demo.Model.BorrowedHistory;

import java.util.List;

public interface BorrowedHistoryService {
    void addHistoryBorrowedBooks(long bookId);
    List<BorrowedHistory> showBorrowedHistory();
    void save(BorrowedHistory borrowedHistory);
}
