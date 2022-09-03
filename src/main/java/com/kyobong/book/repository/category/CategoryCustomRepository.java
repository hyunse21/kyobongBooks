package com.kyobong.book.repository.category;

import com.kyobong.book.domain.category.Category;
import com.kyobong.book.service.category.CategorySearch;

import java.util.List;

public interface CategoryCustomRepository {

    List<Category> search(CategorySearch categorySearch);
}
