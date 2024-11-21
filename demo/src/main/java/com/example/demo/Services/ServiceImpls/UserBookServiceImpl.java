package com.example.demo.Services.ServiceImpls;

import com.example.demo.DTO.ShowBooksBorrowedByUserDTO;
import com.example.demo.Model.Book;
import com.example.demo.Model.UserBook;
import com.example.demo.Respository.UserBookRepository;
import com.example.demo.Services.Service.UserBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<UserBook> getAllBooks() {
        return userBookRepository.findAll();
    }
    public List<ShowBooksBorrowedByUserDTO> getBooksWithBasicInfoForAdmin(long userId) {
        return userBookRepository.getBooksWithBasicInfoForAdmin(userId);
    }
    public List<ShowBooksBorrowedByUserDTO> getBooksWithDetailedInfoForUser(long userId) {
        List<UserBook> userBooks = userBookRepository.findByUserId(userId);

        return userBooks.stream().map(ub -> {
            Book b = ub.getBook();
            return new ShowBooksBorrowedByUserDTO(
                    b.getId(),
                    b.getTitle(),
                    ub.getBorrowDate(),
                    ub.getDueDate(),
                    b.getPublishedDate(),
                    b.getCategories(),
                    b.getAuthors()
            );
        }).collect(Collectors.toList());
    }
}
