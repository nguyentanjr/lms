package com.example.demo.Services.ServiceImpls;

import com.example.demo.Model.UserBook;
import com.example.demo.Respository.UserBookRepository;
import com.example.demo.Services.Service.UserBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class UserBookServiceImpl implements UserBookService {
    @Autowired
    private UserBookRepository userBookRepository;

    public void save(UserBook userBook) {
        userBookRepository.save(userBook);
    }
    public void saveAll(List<UserBook> userBookList) {
        userBookRepository.saveAll(userBookList);
    }
    public boolean hasUserBorrowedBook(long userId, long bookId) {
        return userBookRepository.existsByUserIdAndBookId(userId,bookId) > 0;
    }
}
