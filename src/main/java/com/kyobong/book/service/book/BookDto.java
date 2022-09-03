package com.kyobong.book.service.book;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BookDto {

    private Long id;
    private String title;
    private String author;
    private String rentalYN;
    private String status;
    private List<String> categoryNames;

    public BookDto() {
    }
}
