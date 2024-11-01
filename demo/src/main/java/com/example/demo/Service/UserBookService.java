package com.example.demo.Service;

import com.example.demo.Model.UserBook;
import com.example.demo.Respository.UserBookRepository;
import com.example.demo.Respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBookService {
    @Autowired
    private UserBookRepository userBookRepository;

    public void save(UserBook userBook) {
        userBookRepository.save(userBook);
    }

    public boolean hasUserBorrowedBook(long userId, long bookId) {
        return userBookRepository.existsByUserIdAndBookId(userId,bookId) > 0;
    }
}
