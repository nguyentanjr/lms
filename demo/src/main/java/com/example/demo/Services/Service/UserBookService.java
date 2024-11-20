package com.example.demo.Services.Service;

import com.example.demo.Model.UserBook;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserBookService {
    void save(UserBook userBook);
    void saveAll(List<UserBook> userBookList);
    boolean hasUserBorrowedBook(long userId, long bookId);
    void unassignBookFromUsers(long bookId);

    void deleteRelationByBookId(long bookId);
    void deleteRelationByUserId(long userId);
    List<UserBook> getAllBooks();
}
