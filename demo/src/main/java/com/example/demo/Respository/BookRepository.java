package com.example.demo.Respository;

import com.example.demo.Model.Book;
import com.example.demo.Model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findById(long bookId);
    @Query("SELECT b FROM Book b WHERE b.name LIKE %:name%")
    public List<Book> findBooksByName(@Param("name") String name);

    List<Book> findByGenre(Genre genre);

    //@Query("SELECT a FROM Author a WHERE a.name LIKE %:author_name%")
    List<Book> findBooksByAuthorName(@Param("author_name") String author_name);

    List<Book> findByPublishedYear(int publishedYear);
}
