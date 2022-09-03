package com.kyobong.book.controller.category;

import com.kyobong.book.domain.category.Category;
import com.kyobong.book.service.category.CategorySaveDto;
import com.kyobong.book.service.category.CategorySearch;
import com.kyobong.book.service.category.CategoryService;
import lombok.AllArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/add")
    public JSONObject add(@RequestBody List<CategorySaveDto> addList) {

        List<Category> dataset = new ArrayList<>();
        List<Object> errors = new ArrayList<>();

        for (CategorySaveDto category : addList) {

            try {
                Category saved = categoryService.add(category);
                dataset.add(saved);

            } catch (IllegalArgumentException e) {
                JSONObject errorObj = new JSONObject();
                errorObj.put("data", category);
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
    public List<Category> list(@RequestBody CategorySearch categorySearch) {
        return categoryService.list(categorySearch);
    }

    @PostMapping("/update")
    public JSONObject update(@RequestBody List<CategorySaveDto> updateList) {

        List<Category> dataset = new ArrayList<>();
        List<Object> errors = new ArrayList<>();

        for (CategorySaveDto category : updateList) {

            try {
                Category saved = categoryService.update(category);
                dataset.add(saved);

            } catch (IllegalArgumentException e) {
                JSONObject errorObj = new JSONObject();
                errorObj.put("data", category);
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
    public JSONObject delete(@RequestBody List<String> categoryNames) {

        Long deleteCount = 0L;
        List<Object> errors = new ArrayList<>();

        for (String categoryName : categoryNames) {

            try {
                deleteCount += categoryService.delete(categoryName);

            } catch (IllegalArgumentException e) {
                JSONObject errorObj = new JSONObject();
                errorObj.put("data", categoryName);
                errorObj.put("message", e.getMessage());
                errors.add(errorObj);
            }

        }

        JSONObject response = new JSONObject();
        response.put("deleteCount", deleteCount);
        response.put("errors", errors);

        return response;
    }
}
