package com.example.demo.Services.Service;

import com.example.demo.DTO.ShowBooksBorrowedByUserDTO;
import com.example.demo.Model.UserBook;
import com.example.demo.Respository.UserBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserBookService {
    void save(UserBook userBook);
    void saveAll(List<UserBook> userBookList);
    boolean hasUserBorrowedBook(long userId, long bookId);
    void unassignBookFromUsers(long bookId);
    UserBook findByBookIdAndUserId(long bookId);

    void deleteRelationByBookId(long bookId);
    void deleteRelationByUserId(long userId);
    void deleteByUserIdAndBookId(long bookId);
    List<UserBook> getAllBooks();

    List<ShowBooksBorrowedByUserDTO> getBooksWithBasicInfoForAdmin(long userId);
    List<ShowBooksBorrowedByUserDTO> getBooksWithDetailedInfoForUser(long userId);
    List<Long> getUserIdByBookId(long bookId);
}
