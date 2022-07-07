package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.api.CategoryApi;
import com.epam.spring.homework3.dto.CategoryDto;
import com.epam.spring.homework3.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryApi {

    private final CategoryService categoryService;

    @Override
    public List<CategoryDto> getAllCategories() {
        log.info("find all categories");
        return categoryService.findAll();
    }

    @Override
    public CategoryDto getCategory(Long id) {
        log.info("find category with id {}", id);
        return categoryService.findById(id);
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        log.info("save category");
        return categoryService.save(categoryDto);
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        log.info("update category with id {}", id);
        return categoryService.update(id, categoryDto);
    }

    @Override
    public ResponseEntity<Void> deleteCategory(Long id) {
        log.info("delete category with id {}", id);
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
