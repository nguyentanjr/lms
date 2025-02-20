package com.example.demo.Services.ServiceImpls;

import com.example.demo.DTO.AddBookDTO;
import com.example.demo.DTO.BookDTO;
import com.example.demo.Model.*;
import com.example.demo.Respository.BookRepository;
import com.example.demo.Services.Service.*;
import com.example.demo.config.APIKeyConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private APIKeyConfig apiKeyConfig;
    @Autowired
    private JsonStorageService jsonStorageService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserBookService userBookService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private BorrowedHistoryService borrowedHistoryService;

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    public List<Book> saveALlBooks(List<Book> books) {
        return bookRepository.saveAll(books);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public String saveBooksToJson() {
        List<Book> bookList = bookRepository.findAll();
        Set<String> booksListTitle = bookList.stream().map(Book::getTitle).collect(Collectors.toSet());
        try {
            jsonStorageService.saveBooks(booksListTitle);
            return "Books saved successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to save books.";
        }
    }

    public Book findBookByBookId(long bookId) {
        return bookRepository.findById(bookId);
    }


    @Override
    public List<Book> findBooksByTitle(String title) {
        List<Book> books = bookRepository.findBooksByTitle(title);
        if (books.isEmpty()) {
            fetchBooks(title);
            books = bookRepository.findBooksByTitle(title);
            saveBooksToJson();
        }
        return books;
    }

    @Override
    public List<Book> findBookByAuthor(String author) {
        return bookRepository.findBooksByAuthor(author);
    }

    public List<Book> findBookByCategory(String category) {
        return bookRepository.findBooksByCategory(category);
    }


    public void updateCopiesAvailable(long id, int copies) {
        Book book = bookRepository.findById(id);
        book.setCopiesAvailable(copies);
        bookRepository.save(book);
    }

    @Async("thread_api")
    public void fetchBooks(String title) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + title + "&key=" + apiKeyConfig.getKey();
        String response = restTemplate.getForObject(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode items = jsonNode.get("items");
            for (JsonNode item : items) {
                saveBook(retrieveBookDataFromAPI(item));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Book retrieveBookDataFromAPI(JsonNode item) {
        Book book = new Book();
        String title = item.path("volumeInfo").path("title").asText();
        book.setTitle(title);
        ArrayList<String> authors = new ArrayList<>();
        JsonNode authorsItem = item.path("volumeInfo").path("authors");
        for (JsonNode author : authorsItem) {
            authors.add(author.asText());
        }
        book.setAuthors(authors);
        ArrayList<String> categories = new ArrayList<>();
        JsonNode categoriesItem = item.path("volumeInfo").path("categories");
        for (JsonNode category : categoriesItem) {
            categories.add(category.asText());
        }
        book.setCategories(categories);
        String publishedDate = item.path("volumeInfo").path("publishedDate").asText();
        book.setPublishedDate(publishedDate);
        Random random = new Random();
        int copiesAvailable = random.nextInt(5);
        book.setCopiesAvailable(copiesAvailable);
        book.setHidden(false);
        return book;
    }

    public void removeBookById(long bookId) {
        bookRepository.removeBookById(bookId);
    }

    public List<String> getBookSuggestion(String query) {
        try {
            List<String> booksFromJson = jsonStorageService.loadBooks();
            return booksFromJson.stream().filter(book -> book.toLowerCase().contains(query.toLowerCase()))
                    .toList();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void addBook(AddBookDTO addBookDTO) {
        Book book = new Book();
        book.setTitle(addBookDTO.getTitle());
        book.setAuthors(addBookDTO.getAuthors());
        book.setCategories(addBookDTO.getCategories());
        book.setPublishedDate(addBookDTO.getPublishedDate());
        book.setCopiesAvailable(addBookDTO.getCopies_available());
        bookRepository.save(book);
    }

    public int getBookCopies(long bookId) {
        Book book = findBookByBookId(bookId);
        return book.getCopiesAvailable();
    }

    public boolean checkUserHasBorrowedBook(long bookId) {
        User user = userService.findUserByUserName(userService.getUsername()).get();
        return userBookService.hasUserBorrowedBook(user.getId(), bookId);
    }

    public void saveBookBorrowedByUser(long bookId, long userId,int copies) {
        User user = userService.findUserById(userId);
        Book book = findBookByBookId(bookId);
        UserBook userBook = new UserBook();
        book.setCopiesAvailable(copies);
        userBook.setBook(book);
        userBook.setUser(user);
        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusDays(60);
        userBook.setDueDate(dueDate);
        userBookService.save(userBook);
    }

    public void saveBookFromCart(List<BookDTO> bookDTOList,
                                 @ModelAttribute("cart") Cart cart, HttpSession session) {
        User user = userService.findUserByUserName(userService.getUsername()).get();
        List<UserBook> userBooks = new ArrayList<>();
        for (BookDTO bookDTO : bookDTOList) {
            Book book = modelMapper.map(bookDTO, Book.class);
            userBooks.add(new UserBook(user, book));
            cart.getBookList().clear();
            session.setAttribute("cart", cart);
        }
        userBookService.saveAll(userBooks);
    }

    public void removeBook(long bookId) {
        List<Long> listsUserId = userBookService.getUserIdByBookId(bookId);
        for(Long userId : listsUserId) {
            String username = userService.findUserNameByUserId(userId);
            String message = "ID " + bookId  + ": Due to unforeseen circumstances, we need to remove this from our " +
                    "library. Sorry for the inconvenient!   ";
            notificationService.sendNotificationToUser(username,message);
        }
        userBookService.deleteRelationByBookId(bookId);
        borrowedHistoryService.deleteRelationByBookId(bookId);
        removeBookById(bookId);
        saveBooksToJson();
    }

    public void setHideBook(String status, long bookId) {
        Book book = findBookByBookId(bookId);
        if (status.equals("hide")) {
            book.setHidden(true);
        } else {
            book.setHidden(false);
        }
        saveBook(book);
    }


    public boolean checkBookHidden(long bookId) {
        Book book = findBookByBookId(bookId);
        return book.isHidden();
    }

    public void editBook(Book book) {
        Book myBook = findBookByBookId(book.getId());
        if (!book.getTitle().isEmpty()) {
            myBook.setTitle(book.getTitle());
        }
        if (book.getAuthors() != null) {
            myBook.setAuthors(book.getAuthors());
        }
        if (book.getCategories() != null) {
            myBook.setCategories(book.getCategories());
        }
        if (!book.getPublishedDate().isEmpty()) {
            myBook.setPublishedDate(book.getPublishedDate());
        }
        saveBook(myBook);
    }

    public void returnBook(long bookId) {
        Book book = findBookByBookId(bookId);
        book.setCopiesAvailable(book.getCopiesAvailable() + 1);
        userBookService.deleteByUserIdAndBookId(bookId);
    }
}
