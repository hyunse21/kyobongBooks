package com.kyobong.book.repository.category;

import com.kyobong.book.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, String>, CategoryCustomRepository {

    Category findByName(String name);

    @Modifying
    @Query("delete from Category c where c.name = :name")
    int deleteByNameQuery(@Param("name") String name);

}
