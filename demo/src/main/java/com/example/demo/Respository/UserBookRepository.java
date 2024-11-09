package com.example.demo.Respository;

import com.example.demo.Model.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserBookRepository extends JpaRepository<UserBook,Long> {
    @Query("SELECT COUNT(ub) FROM UserBook ub WHERE ub.user.id = :userId AND ub.book.id = :bookId")
    int existsByUserIdAndBookId(long userId,long bookId);

    @Query("SELECT ub FROM UserBook ub WHERE ub.book.id = :bookId")
    UserBook findByBookId(long bookId);
}
