package com.example.demo.Services.ServiceImpls;

import com.example.demo.DTO.ShowBooksBorrowedByUserDTO;
import com.example.demo.Model.Book;
import com.example.demo.Model.UserBook;
import com.example.demo.Respository.UserBookRepository;
import com.example.demo.Services.Service.BookService;
import com.example.demo.Services.Service.UserBookService;
import com.example.demo.Services.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserBookServiceImpl implements UserBookService {
    @Autowired
    private UserBookRepository userBookRepository;
    @Autowired
    private UserService userService;

    public void save(UserBook userBook) {
        userBookRepository.save(userBook);
    }

    public void saveAll(List<UserBook> userBookList) {
        userBookRepository.saveAll(userBookList);
    }

    public boolean hasUserBorrowedBook(long userId, long bookId) {
        return userBookRepository.existsByUserIdAndBookId(userId, bookId) > 0;
    }

    public void unassignBookFromUsers(long bookId) {
        List<UserBook> userBooks = userBookRepository.findByBookId(bookId);
        for (UserBook userBook : userBooks) {
            userBook.setBook(null);
            userBookRepository.save(userBook);
        }
    }

    public void deleteRelationByBookId(long bookId) {
        userBookRepository.deleteByBookId(bookId);
    }

    public void deleteRelationByUserId(long userId) {
        userBookRepository.deleteByUserId(userId);
    }

    public void deleteByUserIdAndBookId(long bookId) {
        long userId = userService.getUserId();
        userBookRepository.deleteByUserIdAndBookId(userId, bookId);
    }

    public List<UserBook> getAllBooks() {
        return userBookRepository.findAll();
    }

    public List<ShowBooksBorrowedByUserDTO> getBooksWithBasicInfoForAdmin(long userId) {
        return userBookRepository.getBooksWithBasicInfoForAdmin(userId);
    }

    public List<ShowBooksBorrowedByUserDTO> getBooksWithDetailedInfoForUser(long userId) {
        List<UserBook> userBooks = userBookRepository.findByUserId(userId);
        return userBooks.stream().map(userBook -> {
            Book book = userBook.getBook();
            return new ShowBooksBorrowedByUserDTO(
                    book.getId(),
                    book.getTitle(),
                    book.getPublishedDate(),
                    userBook.getBorrowDate(),
                    userBook.getDueDate(),
                    book.getCategories(),
                    book.getAuthors(),
                    userBook.getIsReturned()
            );
        }).collect(Collectors.toList());
    }

    public UserBook findByBookIdAndUserId(long bookId) {
        long userId = userService.getUserId();
        return userBookRepository.findByBookIdAndUserId(bookId, userId);
    }


}
