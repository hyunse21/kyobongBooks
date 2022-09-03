package com.kyobong.book.repository.book;

import com.kyobong.book.domain.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long>, BookCustomRepository {

    @Modifying
    @Query("delete from Book b where b.id = :id")
    int deleteByIdQuery(@Param("id") Long id);
}
