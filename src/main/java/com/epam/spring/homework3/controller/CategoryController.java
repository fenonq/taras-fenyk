package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.dto.CategoryDto;
import com.epam.spring.homework3.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/categories")
    public List<CategoryDto> getAllCategories() {
        log.info("find all categories");
        return categoryService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/category/{id}")
    public CategoryDto getCategory(@PathVariable Long id) {
        log.info("find category with id {}", id);
        return categoryService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/category")
    public CategoryDto createCategory(@RequestBody CategoryDto categoryDto) {
        log.info("save category");
        return categoryService.save(categoryDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/category/{id}")
    public CategoryDto updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        log.info("update category with id {}", id);
        return categoryService.update(id, categoryDto);
    }

    @DeleteMapping(value = "/category/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        log.info("delete category with id {}", id);
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
