package com.example.demo.Service;

import com.example.demo.Model.Book;
import com.example.demo.Model.Enum.Genre;
import com.example.demo.Respository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TransactionService transactionService;
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> saveALlBooks(List<Book> books) {
        return bookRepository.saveAll(books);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookByBookId(long bookId) {
        return bookRepository.findById(bookId);
    }

    public List<Book> findBooksByName(String name) { return bookRepository.findBooksByName(name); }

    public List<Book> findBooksByGenre(Genre genre) {
        return bookRepository.findByGenre(genre);
    }

    public List<Book> findBooksByAuthorName(String authorName) {
        return bookRepository.findByAuthorName(authorName);
    }

    public List<Genre> getAllGenre() {
        return Arrays.asList(Genre.values());
    }

    public List<Book> findBooksByPublishedYear(int publishedYear) {
        return bookRepository.findByPublishedYear(publishedYear);
    }

    public void updateCopiesAvailable(long id,int copies) {
        Book book = bookRepository.findById(id);
        book.setCopiesAvailable(copies);
        bookRepository.save(book);
    }






//    @PersistenceContext
//    private EntityManager entityManager;

//    @Transactional
//    public void dropColumn() {
//        entityManager.createNativeQuery("ALTER TABLE card DROP COLUMN student_studentid")
//                .executeUpdate();
//    }


}
