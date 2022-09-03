package com.kyobong.book.service.category;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategorySaveDto {
    private String name;
    private String description;

}
