package com.kyobong.book.repository.book.Impl;

import com.kyobong.book.repository.book.BookCustomRepository;
import com.kyobong.book.service.book.BookDto;
import com.kyobong.book.service.book.BookSearch;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.kyobong.book.domain.book.QBook.book;
import static com.kyobong.book.domain.book.QBookCategory.bookCategory;

@Repository
public class BookRepositoryImpl implements BookCustomRepository {

    private final JPAQueryFactory queryFactory;

    public BookRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }


    @Override
    public List<BookDto> search(BookSearch bookSearch) {
        return queryFactory
                .select(
                        Projections.fields(
                                BookDto.class,
                                book.id,
                                book.title,
                                book.author,
                                book.rentalYN,
                                book.status
                        )
                )
                .from(book)
                .where(
                        containsTitle(bookSearch.getTitle()),
                        containsAuthor(bookSearch.getAuthor()),
                        eqRentalYN(bookSearch.getRentalYN()),
                        eqStatus(bookSearch.getStatus()),
                        book.id.in(
                                JPAExpressions
                                        .select(bookCategory.bookId)
                                        .from(bookCategory)
                                        .where(
                                                inCategoryNames(bookSearch.getCategoryNames())
                                        )
                        )
                )
                .orderBy(
                        book.id.asc()
                ).fetch();
    }

    private BooleanExpression containsTitle(String title) {
        return !StringUtils.hasText(title) ? null : book.title.contains(title);
    }

    private BooleanExpression containsAuthor(String author) {
        return !StringUtils.hasText(author) ? null : book.author.contains(author);
    }

    private BooleanExpression eqRentalYN(String rentalYN) {
        return !StringUtils.hasText(rentalYN) ? null : book.rentalYN.eq(rentalYN);
    }

    private BooleanExpression eqStatus(String status) {
        return !StringUtils.hasText(status) ? null : book.status.eq(status);
    }

    private BooleanExpression inCategoryNames(List<String> categoryNames) {
        return categoryNames == null || categoryNames.isEmpty() ?
                null : bookCategory.categoryName.in(categoryNames);
    }
}
