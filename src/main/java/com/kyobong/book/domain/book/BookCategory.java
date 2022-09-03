package com.kyobong.book.domain.book;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Data
@Entity
@IdClass(BookCategoryId.class)
public class BookCategory {

    @Id
    private Long bookId;
    @Id
    private String categoryName;

    public BookCategory() {
    }

    public BookCategory(Long bookId, String categoryName) {
        this.bookId = bookId;
        this.categoryName = categoryName;
    }
}
