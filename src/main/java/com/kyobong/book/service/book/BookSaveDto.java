package com.kyobong.book.service.book;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BookSaveDto {
    private Long id;
    private String title;
    private String author;
    private String rentalYN;
    private String status;
    private List<String> categoryNames;

    public BookSaveDto() {
    }

    public BookSaveDto(String title, String author, String rentalYN, String status, List<String> categoryNames) {
        this.title = title;
        this.author = author;
        this.rentalYN = rentalYN;
        this.status = status;
        this.categoryNames = categoryNames;
    }
}
