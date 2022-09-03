package com.kyobong.book.repository.book;

import com.kyobong.book.domain.book.BookCategory;
import com.kyobong.book.domain.book.BookCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookCategoryRepository extends JpaRepository<BookCategory, BookCategoryId> {

    List<BookCategory> findByBookId(Long bookId);

    List<BookCategory> findByCategoryName(String categoryName);

    @Modifying
    @Query("delete from BookCategory bc where bc.bookId = :bookId")
    int deleteByBookIdQuery(@Param("bookId") Long bookId);

    @Modifying
    @Query("delete from BookCategory bc where bc.categoryName = :categoryName")
    int deleteByCategoryNameQuery(@Param("categoryName") String categoryName);
}
