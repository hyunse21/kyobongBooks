package com.kyobong.book.repository.category.Impl;

import com.kyobong.book.domain.category.Category;
import com.kyobong.book.repository.category.CategoryCustomRepository;
import com.kyobong.book.service.category.CategorySearch;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.kyobong.book.domain.category.QCategory.category;

@Repository
public class CategoryRepositoryImpl implements CategoryCustomRepository {

    private final JPAQueryFactory queryFactory;

    public CategoryRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Category> search(CategorySearch categorySearch) {
        return queryFactory
                .selectFrom(category)
                .where(
                        containsName(categorySearch.getName()),
                        containsDescription(categorySearch.getDescription())
                )
                .orderBy(
                        category.name.asc()
                ).fetch();
    }

    private BooleanExpression containsName(String name) {
        return !StringUtils.hasText(name) ? null : category.name.contains(name);
    }

    private BooleanExpression containsDescription(String description) {
        return !StringUtils.hasText(description) ? null : category.description.contains(description);
    }
}
