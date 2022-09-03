package com.kyobong.book.domain.category;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Category {

    @Id
    private String name;
    private String description;

    public Category() {
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
