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

    @Query("SELECT ub FROM UserBook ub WHERE ub.book.id = :bookId")
    List<UserBook> findByBookId(long bookId);

    @Modifying
    @Transactional
    void deleteByBookId(long bookId);

    @Modifying
    @Transactional
    void deleteByUserId(long userId);

    List<UserBook> findAll();

    @Query("SELECT new com.example.demo.DTO.ShowBooksBorrowedByUserDTO(b.id,b.title,ub.borrowDate,ub.dueDate) " +
            "FROM UserBook ub JOIN ub.book b WHERE ub.user.id =:userId")
    List<ShowBooksBorrowedByUserDTO>getBooksWithBasicInfoForAdmin(long userId);

    public interface BookBorrowedProjection {
        Long getId();
        String getTitle();
        LocalDate getBorrowDate();
        LocalDate getDueDate();
        String getPublishedDate();
        List<String> getCategories();
        List<String> getAuthors();
    }

    List<UserBook> findByUserId(long userId);
    @Query(value = "SELECT " +
            "b.book_id as id, " +
            "b.title as title, " +
            "ub.borrow_date as dateBorrowed, " +
            "ub.due_date as dueDate, " +
            "b.published_date as publishedDate, " +
            "GROUP_CONCAT(DISTINCT bc.categories) as categories, " +
            "GROUP_CONCAT(DISTINCT ba.author_name) as authors " +
            "FROM user_book ub " +
            "JOIN book b ON ub.book_id = b.book_id " +
            "LEFT JOIN book_categories bc ON b.book_id = bc.book_id " +
            "LEFT JOIN book_authors ba ON b.book_id = ba.book_id " +
            "WHERE ub.user_id = :userId " +
            "GROUP BY b.book_id, b.title, ub.borrow_date, ub.due_date, b.published_date",
            nativeQuery = true)
    List<ShowBooksBorrowedByUserDTO> getBooksWithDetailedInfoForUser(@Param("userId") long userId);

}
