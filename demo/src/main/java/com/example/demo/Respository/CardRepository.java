package com.example.demo.Respository;

import com.example.demo.Model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends JpaRepository<Card, Long> {
    public Card findById(long cardId);

    @Query("SELECT c FROM Card c WHERE c.student.studentid = :student_id")
    public Card findByStudentId(@Param("student_id") long student_id);
}
