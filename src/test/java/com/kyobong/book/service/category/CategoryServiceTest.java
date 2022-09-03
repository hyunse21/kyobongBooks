package com.kyobong.book.service.category;

import com.kyobong.book.domain.category.Category;
import com.kyobong.book.repository.book.BookCategoryRepository;
import com.kyobong.book.repository.book.BookRepository;
import com.kyobong.book.repository.category.CategoryRepository;
import com.kyobong.book.service.book.BookSaveDto;
import com.kyobong.book.service.book.BookService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    BookService bookService;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    BookCategoryRepository bookCategoryRepository;

    @BeforeEach
    void beforeEach() {
        bookCategoryRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void list() {
        // 검색 테스트
        categoryService.add(new CategorySaveDto("문학", "설명1"));
        categoryService.add(new CategorySaveDto("경제경영", "설명2"));
        categoryService.add(new CategorySaveDto("인문학", "설명3"));

        CategorySearch categorySearch = new CategorySearch();
        categorySearch.setName("학");
        categorySearch.setName("3");
        List<Category> list = categoryService.list(categorySearch);

        for (Category category : list) {
            Assertions.assertThat(category.getName()).contains(categorySearch.getName());
            Assertions.assertThat(category.getDescription()).contains(categorySearch.getDescription());
        }
    }

    @Test
    void add() {
        // 저장 테스트
        CategorySaveDto newCategory = new CategorySaveDto("문학", "설명1");
        Category saved = categoryService.add(newCategory);

        Category found = categoryRepository.findByName(saved.getName());
        Assertions.assertThat(saved.getName()).isEqualTo(found.getName());
        Assertions.assertThat(saved.getDescription()).isEqualTo(found.getDescription());
    }

    @Test
    void update() {
        // 변경 테스트
        CategorySaveDto newCategory = new CategorySaveDto("문학", "설명1");
        Category saved = categoryService.add(newCategory);

        CategorySaveDto updateCategory = new CategorySaveDto(saved.getName(), "설명11");
        categoryService.update(updateCategory);

        Category found = categoryRepository.findByName(saved.getName());
        Assertions.assertThat(updateCategory.getDescription()).isEqualTo(found.getDescription());

    }

    @Test
    void delete() {
        CategorySaveDto newCategory = new CategorySaveDto("문학", "설명1");
        Category saved = categoryService.add(newCategory);

        int deleteCount = categoryService.delete(saved.getName());
        Assertions.assertThat(deleteCount).isEqualTo(1);

        Category deleted = categoryRepository.findByName(saved.getName());
        Assertions.assertThat(deleted).isEqualTo(null);
    }

    @Test
    void noCategoryNameSave() {
        CategorySaveDto newCategory = new CategorySaveDto(null, "설명1");
        Assertions.assertThatThrownBy(() -> categoryService.add(newCategory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("카테고리명 입력은 필수입니다.");
    }

    @Test
    void existCategoryAdd() {
        CategorySaveDto newCategory1 = new CategorySaveDto("문학", "설명1");
        categoryService.add(newCategory1);

        CategorySaveDto newCategory2 = new CategorySaveDto("문학", "설명2");
        Assertions.assertThatThrownBy(() -> categoryService.add(newCategory2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("카테고리명이 이미 존재합니다.");
    }

    @Test
    void notExistCategoryUpdate() {
        categoryService.add(new CategorySaveDto("문학", "설명1"));
        CategorySaveDto updateCategory = new CategorySaveDto("IT", "설명11");
        Assertions.assertThatThrownBy(() -> categoryService.update(updateCategory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("카테고리가 존재하지 않습니다.");
    }

    @Test
    void inUseCategoryDelete() {
        CategorySaveDto newCategory = new CategorySaveDto("문학", "설명1");
        Category savedCategory = categoryService.add(newCategory);

        BookSaveDto newBook = new BookSaveDto("너에게 해주지 못한 말들", "권태영", "Y", "정상", Arrays.asList("문학"));
        bookService.add(newBook);

        Assertions.assertThatThrownBy(() -> categoryService.delete(savedCategory.getName()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("사용중인 카테고리는 삭제할 수 없습니다.");
    }

}