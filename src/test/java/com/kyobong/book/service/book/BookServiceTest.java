package com.kyobong.book.service.book;

import com.kyobong.book.domain.book.Book;
import com.kyobong.book.domain.book.BookCategory;
import com.kyobong.book.repository.book.BookCategoryRepository;
import com.kyobong.book.repository.book.BookRepository;
import com.kyobong.book.repository.category.CategoryRepository;
import com.kyobong.book.service.category.CategorySaveDto;
import com.kyobong.book.service.category.CategoryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceTest {

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
        categoryRepository.deleteAll();
        categoryService.add(new CategorySaveDto("문학", ""));
        categoryService.add(new CategorySaveDto("경제경영", ""));
        categoryService.add(new CategorySaveDto("인문학", ""));
        categoryService.add(new CategorySaveDto("IT", ""));
        categoryService.add(new CategorySaveDto("과학", ""));

        bookCategoryRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    void list() {

        // 검색 테스트
        bookService.add(new BookSaveDto("너에게 해주지 못한 말들", "권태영", "N", "분실", Arrays.asList("문학")));
        bookService.add(new BookSaveDto("단순하게 배부르게", "현영서", "Y", "정상", Arrays.asList("문학")));
        bookService.add(new BookSaveDto("트랜드 코리아 2322", "권태영", "N", "훼손", Arrays.asList("경제경영")));
        bookService.add(new BookSaveDto("초격자 투자", "장동혁", "Y", "훼손", Arrays.asList("경제경영")));

        // 지은이, 카테고리 검색
        BookSearch bookSearch1 = new BookSearch();
        bookSearch1.setAuthor("권태");
        bookSearch1.setCategoryNames(Arrays.asList("문학", "경제경영"));
        List<BookDto> list1 = bookService.list(bookSearch1);

        for (BookDto book : list1) {
            Assertions.assertThat(book.getAuthor()).contains(bookSearch1.getAuthor());
            Assertions.assertThat(book.getCategoryNames()).containsAnyOf("문학", "경제경영");
        }

        // 제목 검색
        BookSearch bookSearch2 = new BookSearch();
        bookSearch2.setTitle("게");
        List<BookDto> list2 = bookService.list(bookSearch2);

        for (BookDto book : list2) {
            Assertions.assertThat(book.getTitle()).contains(bookSearch2.getTitle());
        }

        // 대여가능 여부, 상태 검색
        BookSearch bookSearch3 = new BookSearch();
        bookSearch3.setRentalYN("N");
        bookSearch3.setStatus("훼손");
        List<BookDto> list3 = bookService.list(bookSearch3);

        for (BookDto book : list3) {
            Assertions.assertThat(book.getRentalYN()).isEqualTo(bookSearch3.getRentalYN());
            Assertions.assertThat(book.getStatus()).isEqualTo(bookSearch3.getStatus());
        }

    }

    @Test
    void add() {

        // 저장 테스트
        BookSaveDto newBook = new BookSaveDto("너에게 해주지 못한 말들", "권태영", "Y", "정상", Arrays.asList("문학", "경제경영"));
        BookDto savedBookDto = bookService.add(newBook);

        Book savedBook = bookRepository.findById(savedBookDto.getId()).orElseGet(null);
        Assertions.assertThat(savedBook.getId()).isGreaterThan(0);
        Assertions.assertThat(savedBook.getTitle()).isEqualTo(newBook.getTitle());
        Assertions.assertThat(savedBook.getAuthor()).isEqualTo(newBook.getAuthor());
        Assertions.assertThat(savedBook.getRentalYN()).isEqualTo(newBook.getRentalYN());
        Assertions.assertThat(savedBook.getStatus()).isEqualTo(newBook.getStatus());

        List<BookCategory> savedBookCate = bookCategoryRepository.findByBookId(savedBook.getId());
        List<String> savedBookCateNames = savedBookCate.stream()
                .map(bookCategory -> bookCategory.getCategoryName())
                .collect(Collectors.toList());
        Assertions.assertThat(savedBookCateNames).containsOnly("문학", "경제경영");

    }

    @Test
    void noCategoryBookSave() {
        // 카테고리가 없을 경우
        BookSaveDto newBook = new BookSaveDto("너에게 해주지 못한 말들", "권태영", "Y", "정상", new ArrayList<>());
        Assertions.assertThatThrownBy(() -> bookService.add(newBook))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("카테고리 입력은 필수입니다.");
    }

    @Test
    void notExistCategoryBookSave() {
        // 존재하지 않는 카테고리일 경우
        BookSaveDto newBook1 = new BookSaveDto("너에게 해주지 못한 말들", "권태영", "Y", "정상", Arrays.asList("여행"));
        Assertions.assertThatThrownBy(() -> bookService.add(newBook1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("카테고리명이 존재하지 않습니다.");

        BookDto newBook2 = bookService.add(new BookSaveDto("너에게 해주지 못한 말들", "권태영", "Y", "정상", Arrays.asList("문학")));
        BookSaveDto updateBook = new BookSaveDto(newBook2.getId(), null, null, null, null, Arrays.asList("여행"));
        Assertions.assertThatThrownBy(() -> bookService.update(updateBook))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("카테고리명이 존재하지 않습니다.");
    }


    @Test
    void update() {

        // 변경 테스트
        List<String> newBookCateNames = Arrays.asList("문학", "경제경영");
        BookSaveDto newBook = new BookSaveDto("너에게 해주지 못한 말들", "권태영", "Y", "정상", newBookCateNames);
        BookDto oldBookDto = bookService.add(newBook);

        // 제목, 지은이, 대여가능 여부, 상태, 카테고리 변경
        List<String> updateBookCateNames = Arrays.asList("IT", "과학");
        BookSaveDto updateBook = new BookSaveDto(oldBookDto.getId(), "너에게 해준 말들", "김태영", "N", "분실", updateBookCateNames);
        bookService.update(updateBook);

        Book savedBook = bookRepository.findById(oldBookDto.getId()).orElseGet(null);
        Assertions.assertThat(savedBook.getTitle()).isEqualTo(updateBook.getTitle());
        Assertions.assertThat(savedBook.getAuthor()).isEqualTo(updateBook.getAuthor());
        Assertions.assertThat(savedBook.getRentalYN()).isEqualTo(updateBook.getRentalYN());
        Assertions.assertThat(savedBook.getStatus()).isEqualTo(updateBook.getStatus());

        List<BookCategory> savedBookCate = bookCategoryRepository.findByBookId(oldBookDto.getId());
        List<String> savedBookCateNames = savedBookCate.stream()
                .map(bookCategory -> bookCategory.getCategoryName())
                .collect(Collectors.toList());
        Assertions.assertThat(savedBookCateNames).containsOnly("IT", "과학");


        // ID가 존재하지 않는 경우
        BookSaveDto notExistBook = new BookSaveDto(9999L, "너에게 해준 말들", null, null, null, new ArrayList<>());
        Assertions.assertThatThrownBy(() -> bookService.update(notExistBook))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ID가 존재하지 않습니다.");

    }

    @Test
    void delete() {

        // 삭제 테스트
        List<String> newBookCateNames = Arrays.asList("문학", "경제경영");
        BookSaveDto newBook = new BookSaveDto("너에게 해주지 못한 말들", "권태영", "Y", "정상", newBookCateNames);
        BookDto oldBookDto = bookService.add(newBook);

        int deleteCount = bookService.delete(oldBookDto.getId());
        Assertions.assertThat(deleteCount).isEqualTo(1);

        Optional<Book> deleted = bookRepository.findById(oldBookDto.getId());
        Assertions.assertThat(deleted.isEmpty()).isEqualTo(true);
    }

}