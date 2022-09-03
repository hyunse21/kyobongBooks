package com.kyobong.book.service.book;

import lombok.Data;

import java.util.List;

@Data
public class BookSearch {

    private String title;
    private String author;
    private String rentalYN;
    private String status;
    private List<String> categoryNames;
}
