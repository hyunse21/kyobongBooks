package com.kyobong.book.service.category;

import com.kyobong.book.domain.book.BookCategory;
import com.kyobong.book.domain.category.Category;
import com.kyobong.book.repository.book.BookCategoryRepository;
import com.kyobong.book.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final BookCategoryRepository bookCategoryRepository;

    public List<Category> list(CategorySearch categorySearch) {
        return categoryRepository.search(categorySearch);
    }

    public Category add(CategorySaveDto category) {

        if (!StringUtils.hasText(category.getName())) {
            throw new IllegalArgumentException("카테고리명 입력은 필수입니다.");
        }

        Category found = categoryRepository.findByName(category.getName());

        if (found != null) {
            throw new IllegalArgumentException("카테고리명이 이미 존재합니다.");
        }

        Category newCategory = new Category(category.getName(), category.getDescription());
        categoryRepository.save(newCategory);

        return newCategory;
    }

    public Category update(CategorySaveDto category) {

        Category oldCategory;

        if (!StringUtils.hasText(category.getName())) {
            throw new IllegalArgumentException("카테고리가 존재하지 않습니다.");
        } else {
            oldCategory = categoryRepository.findByName(category.getName());
            if (oldCategory == null) {
                throw new IllegalArgumentException("카테고리가 존재하지 않습니다.");
            }
        }

        if (StringUtils.hasText(category.getDescription())) oldCategory.setDescription(category.getDescription());

        categoryRepository.save(oldCategory);

        return oldCategory;
    }

    @Transactional
    public int delete(String categoryName) {
        List<BookCategory> bookCategories = bookCategoryRepository.findByCategoryName(categoryName);
        if (!bookCategories.isEmpty()) {
            throw new IllegalArgumentException("사용중인 카테고리는 삭제할 수 없습니다.");
        }

        bookCategoryRepository.deleteByCategoryNameQuery(categoryName);
        return categoryRepository.deleteByNameQuery(categoryName);
    }
}
