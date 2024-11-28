package com.example.demo.Respository;

import com.example.demo.Model.BorrowedHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowedHistoryRepository extends JpaRepository<BorrowedHistory, Long> {
    @Query("SELECT bh FROM BorrowedHistory bh WHERE bh.user.id = :userId")
    List<BorrowedHistory> findByUserId(long userId);
}