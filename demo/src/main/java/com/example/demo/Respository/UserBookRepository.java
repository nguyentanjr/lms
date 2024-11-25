package com.example.demo.Respository;

import com.example.demo.DTO.ShowBooksBorrowedByUserDTO;
import com.example.demo.Model.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface UserBookRepository extends JpaRepository<UserBook,Long> {
    @Query("SELECT COUNT(ub) FROM UserBook ub WHERE ub.user.id = :userId AND ub.book.id = :bookId")
    int existsByUserIdAndBookId(long userId,long bookId);

    @Query("SELECT ub FROM UserBook ub WHERE ub.book.id = :bookId AND ub.user.id = :userId")
    UserBook findByBookIdAndUserId(long bookId,long userId);

    @Query("SELECT ub FROM UserBook ub WHERE ub.book.id = :bookId")
    List<UserBook> findByBookId(long bookId);

    @Query("SELECT ub FROM UserBook ub WHERE ub.user.id = :userId")
    List<UserBook> findByUserId(long userId);
    @Modifying
    @Transactional
    void deleteByBookId(long bookId);

    @Modifying
    @Transactional
    void deleteByUserId(long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserBook ub WHERE ub.user.id = :userId AND ub.book.id = :bookId")
    void deleteByUserIdAndBookId(long userId,long bookId);

    List<UserBook> findAll();

    @Query("SELECT new com.example.demo.DTO.ShowBooksBorrowedByUserDTO(b.id,b.title,ub.borrowDate,ub.dueDate) " +
            "FROM UserBook ub JOIN ub.book b WHERE ub.user.id =:userId")
    List<ShowBooksBorrowedByUserDTO>getBooksWithBasicInfoForAdmin(long userId);


}
