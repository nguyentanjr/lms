package com.example.demo.Services.ServiceImpls;

import com.example.demo.Model.Book;
import com.example.demo.Respository.BookRepository;
import com.example.demo.Services.Service.BookService;
import com.example.demo.config.APIKeyConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private APIKeyConfig apiKeyConfig;
    public void saveBook(Book book) {
         bookRepository.save(book);
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

    @Override
    public List<Book> findBooksByTitle(String title) {
        List<Book> books = bookRepository.findBooksByTitle(title);
        if(books.isEmpty()) {
            fetchBooks(title);
            books = bookRepository.findBooksByTitle(title);
        }
        return books;
    }

    @Override
    public List<Book> findBookByAuthor(String authorName) {
        return bookRepository.findBooksByAuthor(authorName);
    }

    public List<Book> findBooksByName(String name) {
        return bookRepository.findBooksByTitle(name);
    }


    public void updateCopiesAvailable(long id, int copies) {
        Book book = bookRepository.findById(id);
        book.setCopiesAvailable(copies);
        bookRepository.save(book);
    }

    @Async("thread_api")
    public void fetchBooks(String title) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + title + "&key=" + apiKeyConfig.getKey();
        String response = restTemplate.getForObject(url,String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode items = jsonNode.get("items");
            for(JsonNode item : items) {
                saveBook(retrieveBookDataFromAPI(item));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Book retrieveBookDataFromAPI(JsonNode item) {
        Book book = new Book();
        String title = item.path("volumeInfo").path("title").asText();
        book.setTitle(title);
        List<String> authors = new ArrayList<>();
        JsonNode authorsItem = item.path("volumeInfo").path("authors");
        for(JsonNode author : authorsItem) {
            authors.add(author.asText());
        }
        book.setAuthors(authors);
        List<String> categories = new ArrayList<>();
        JsonNode categoriesItem = item.path("volumeInfo").path("categories");
        for(JsonNode category : categoriesItem) {
            categories.add(category.asText());
        }
        book.setCategories(categories);
        String publishedDate = item.path("volumeInfo").path("publishedDate").asText();
        book.setPublishedDate(publishedDate);
        Random random = new Random();
        int copiesAvailable = random.nextInt(5);
        book.setCopiesAvailable(copiesAvailable);
        return book;
    }
}
