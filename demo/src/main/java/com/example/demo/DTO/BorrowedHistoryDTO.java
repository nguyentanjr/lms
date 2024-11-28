package com.example.demo.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BorrowedHistoryDTO {
    private long id;
    private String title;
    private String publishedDate;
    private List<String> categories;
    private List<String> authors;
    private LocalDate dateBorrowed;
    private LocalDate dueDate;
    private LocalDate dateReturn;
    private String returnStatus;

    public BorrowedHistoryDTO(long id, String publishedDate, List<String> categories, List<String> authors,
                              LocalDate dateBorrowed, LocalDate dueDate, LocalDate dateReturn, String returnStatus) {
        this.id = id;
        this.publishedDate = publishedDate;
        this.categories = categories;
        this.authors = authors;
        this.dateBorrowed = dateBorrowed;
        this.dueDate = dueDate;
        this.dateReturn = dateReturn;
        this.returnStatus = returnStatus;
    }
}
