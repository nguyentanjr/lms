package com.example.demo.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class BookDTO {
    private long id;
    private String title;
    private List<String> categories;
    private List<String> authors;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDTO bookDTO = (BookDTO) o;
        return id == bookDTO.id && Objects.equals(title, bookDTO.title) && Objects.equals(categories, bookDTO.categories) && Objects.equals(authors, bookDTO.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, categories, authors);
    }
}
