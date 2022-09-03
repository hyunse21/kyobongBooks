package com.kyobong.book.domain.book;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String rentalYN;
    private String status;

    public Book() {
    }

    public Book(String title, String author, String rentalYN, String status) {
        this.title = title;
        this.author = author;
        this.rentalYN = rentalYN;
        this.status = status;
    }
}
