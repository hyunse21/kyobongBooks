package com.kyobong.book.service.book;

import com.kyobong.book.domain.book.Book;
import com.kyobong.book.domain.book.BookCategory;
import com.kyobong.book.domain.category.Category;
import com.kyobong.book.repository.book.BookCategoryRepository;
import com.kyobong.book.repository.book.BookRepository;
import com.kyobong.book.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookCategoryRepository bookCategoryRepository;
    private final CategoryRepository categoryRepository;

    public List<BookDto> list(BookSearch bookSearch) {
        List<BookDto> books = bookRepository.search(bookSearch);

        for (BookDto book : books) {
            List<BookCategory> bookCategories = bookCategoryRepository.findByBookId(book.getId());
            List<String> categoryNames = bookCategories.stream()
                    .map(bookCategory -> bookCategory.getCategoryName())
                    .collect(Collectors.toList());
            book.setCategoryNames(categoryNames);
        }
        return books;
    }

    public BookDto add(BookSaveDto book) {
        if (book.getCategoryNames() == null || book.getCategoryNames().isEmpty()) {
            throw new IllegalArgumentException("카테고리 입력은 필수입니다.");
        }

        // 중복 제거
        book.setCategoryNames(book.getCategoryNames().stream().distinct().collect(Collectors.toList()));

        // 카테고리 ID 존재하는지 체크
        List<String> existCategoryNames = new ArrayList<>();
        for (String categoryName : book.getCategoryNames()) {
            Category found = categoryRepository.findByName(categoryName);

            if (found == null) {
                throw new IllegalArgumentException("카테고리명이 존재하지 않습니다. (카테고리명 : " + categoryName + ")");
            }

            existCategoryNames.add(categoryName);
        }

        if (existCategoryNames.isEmpty()) {
            throw new IllegalArgumentException("카테고리 입력은 필수입니다.");
        }

        // 대여가능 여부 체크
        if (book.getRentalYN() == null || !book.getRentalYN().equalsIgnoreCase("N")) {
            book.setRentalYN("Y");
        } else {
            book.setRentalYN("N");
        }

        // 상태 기본값
        if (book.getStatus() == null) {
            book.setStatus("정상");
        }

        Book newBook = new Book(book.getTitle(), book.getAuthor(), book.getRentalYN(), book.getStatus());
        bookRepository.save(newBook);
        bookRepository.flush();

        for (String categoryName : existCategoryNames) {
            bookCategoryRepository.save(new BookCategory(newBook.getId(), categoryName));
        }

        BookDto saved = new BookDto(newBook.getId(), newBook.getTitle(), newBook.getAuthor(), newBook.getRentalYN(),
                newBook.getStatus(), existCategoryNames);
        return saved;
    }

    @Transactional
    public BookDto update(BookSaveDto book) {
        Book oldBook;

        if (book.getId() == null) {
            throw new IllegalArgumentException("ID가 존재하지 않습니다.");
        } else {
            Optional<Book> found = bookRepository.findById(book.getId());
            if (found.isEmpty()) {
                throw new IllegalArgumentException("ID가 존재하지 않습니다.");
            }
            oldBook = found.get();
        }

        // 대여가능 여부 체크
        if (book.getRentalYN() == null || !book.getRentalYN().equalsIgnoreCase("N")) {
            book.setRentalYN("Y");
        } else {
            book.setRentalYN("N");
        }

        if (StringUtils.hasText(book.getTitle())) oldBook.setTitle(book.getTitle());
        if (StringUtils.hasText(book.getAuthor())) oldBook.setAuthor(book.getAuthor());
        if (StringUtils.hasText(book.getRentalYN())) oldBook.setRentalYN(book.getRentalYN());
        if (StringUtils.hasText(book.getStatus())) oldBook.setStatus(book.getStatus());

        bookRepository.save(oldBook);


        if (book.getCategoryNames() != null) {

            // 중복 제거
            book.setCategoryNames(book.getCategoryNames().stream().distinct().collect(Collectors.toList()));


            // 카테고리 ID 존재하는지 체크
            List<String> existCategoryNames = new ArrayList<>();
            for (String categoryName : book.getCategoryNames()) {
                Category found = categoryRepository.findByName(categoryName);

                if (found == null) {
                    throw new IllegalArgumentException("카테고리명이 존재하지 않습니다. (카테고리명 : " + categoryName + ")");
                }

                existCategoryNames.add(categoryName);
            }

            // 기존 카테고리 삭제
            bookCategoryRepository.deleteByBookIdQuery(oldBook.getId());

            for (String categoryName : existCategoryNames) {
                bookCategoryRepository.save(new BookCategory(oldBook.getId(), categoryName));
            }

        }

        List<BookCategory> bookCategories = bookCategoryRepository.findByBookId(oldBook.getId());
        List<String> categoryNames = bookCategories.stream()
                .map(bookCategory -> bookCategory.getCategoryName())
                .collect(Collectors.toList());

        BookDto saved = new BookDto(oldBook.getId(), oldBook.getTitle(), oldBook.getAuthor(), oldBook.getRentalYN(),
                oldBook.getStatus(), categoryNames);
        return saved;
    }

    @Transactional
    public int delete(Long id) {
        bookCategoryRepository.deleteByBookIdQuery(id);
        return bookRepository.deleteByIdQuery(id);
    }


}
