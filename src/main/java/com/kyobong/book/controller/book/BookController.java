package com.kyobong.book.controller.book;

import com.kyobong.book.service.book.BookDto;
import com.kyobong.book.service.book.BookSaveDto;
import com.kyobong.book.service.book.BookSearch;
import com.kyobong.book.service.book.BookService;
import lombok.AllArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @PostMapping("/add")
    public JSONObject add(@RequestBody List<BookSaveDto> addList) {

        List<BookDto> dataset = new ArrayList<>();
        List<Object> errors = new ArrayList<>();

        for (BookSaveDto book : addList) {
            try {
                BookDto bookDto = bookService.add(book);
                dataset.add(bookDto);
            } catch (IllegalArgumentException e) {
                JSONObject errorObj = new JSONObject();
                errorObj.put("data", book);
                errorObj.put("message", e.getMessage());
                errors.add(errorObj);
            }
        }

        JSONObject response = new JSONObject();
        response.put("dataset", dataset);
        response.put("errors", errors);

        return response;
    }

    @PostMapping("/list")
    public List<BookDto> list(@RequestBody BookSearch bookSearch) {
        return bookService.list(bookSearch);
    }

    @PostMapping("/update")
    public JSONObject update(@RequestBody List<BookSaveDto> updateList) {

        List<BookDto> dataset = new ArrayList<>();
        List<Object> errors = new ArrayList<>();

        for (BookSaveDto book : updateList) {
            try {
                BookDto bookDto = bookService.update(book);
                dataset.add(bookDto);
            } catch (IllegalArgumentException e) {
                JSONObject errorObj = new JSONObject();
                errorObj.put("data", book);
                errorObj.put("message", e.getMessage());
                errors.add(errorObj);
            }
        }

        JSONObject response = new JSONObject();
        response.put("dataset", dataset);
        response.put("errors", errors);

        return response;
    }

    @PostMapping("/delete")
    public JSONObject delete(@RequestBody List<Long> ids) {

        Long deleteCount = 0L;
        List<Object> errors = new ArrayList<>();

        for (Long id : ids) {
            deleteCount += bookService.delete(id);
        }

        JSONObject response = new JSONObject();
        response.put("deleteCount", deleteCount);
        response.put("errors", errors);

        return response;
    }
}
