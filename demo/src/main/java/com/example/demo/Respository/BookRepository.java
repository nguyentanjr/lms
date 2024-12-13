package com.example.demo.Respository;

import com.example.demo.Model.Book;
import com.example.demo.Model.Enum.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findById(long bookId);
    @Query("SELECT b FROM Book b WHERE b.title LIKE %:title%")
    public List<Book> findBooksByTitle(String title);
    @Query("SELECT b FROM Book b WHERE :author MEMBER OF b.authors")
    public List<Book> findBooksByAuthor(String author);

    @Query("SELECT b FROM Book b WHERE :category MEMBER OF b.categories")
    public List<Book> findBooksByCategory(String category);

    @Modifying
    @Transactional
    @Query("DELETE FROM Book b WHERE b.id = :bookId")
    public void removeBookById(long bookId);

}
