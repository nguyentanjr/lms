package com.example.demo.Respository;

import com.example.demo.DTO.BookReservationDTO;
import com.example.demo.Model.BookReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookReservationRepository extends JpaRepository<BookReservation, Long> {
    @Query("SELECT new com.example.demo.DTO.BookReservationDTO(b.id,b.title,br.reservationDate,br.status) " +
            "FROM BookReservation br JOIN br.book b WHERE br.user.id = :userId")
    List<BookReservationDTO> getReservationBook(long userId);

    @Query("SELECT COUNT(br) FROM BookReservation br WHERE br.book.id = :bookId AND br.user.id = :userId " +
            "AND br.status = 'Pending'")
    int checkUserHasReserveBook(long bookId,long userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM BookReservation br WHERE br.book.id = :bookId")
    void deleteBookReservationById(long bookId);

    @Query("SELECT br FROM BookReservation br")
    List<BookReservation> findAll();

    @Modifying
    @Transactional
    void deleteByBookId(long bookId);

}
