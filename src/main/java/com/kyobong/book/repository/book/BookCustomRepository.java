package com.kyobong.book.repository.book;

import com.kyobong.book.service.book.BookDto;
import com.kyobong.book.service.book.BookSearch;

import java.util.List;

public interface BookCustomRepository {

    List<BookDto> search(BookSearch bookSearch);
}
