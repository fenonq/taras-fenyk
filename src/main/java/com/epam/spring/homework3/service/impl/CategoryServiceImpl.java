package com.epam.spring.homework3.service.impl;

import com.epam.spring.homework3.dto.CategoryDto;
import com.epam.spring.homework3.exception.EntityNotFoundException;
import com.epam.spring.homework3.mapper.CategoryMapper;
import com.epam.spring.homework3.model.Category;
import com.epam.spring.homework3.model.Dish;
import com.epam.spring.homework3.repository.CategoryRepository;
import com.epam.spring.homework3.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> findAll() {
        log.info("find all categories");
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper.INSTANCE::mapCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto findById(Long id) {
        log.info("find category with id {}", id);
        Category category = categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return CategoryMapper.INSTANCE.mapCategoryDto(category);
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        log.info("save category");
        Category category = CategoryMapper.INSTANCE.mapCategory(categoryDto);
        category.setVisible(true);
        category = categoryRepository.save(category);
        return CategoryMapper.INSTANCE.mapCategoryDto(category);
    }

    @Override
    @Transactional
    public CategoryDto update(Long id, CategoryDto categoryDto) {
        log.info("update category with id {}", id);
        Category existingCategory = categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Category category = CategoryMapper.INSTANCE.mapCategory(categoryDto);
        category.setDishes(existingCategory.getDishes());
        category.setVisible(existingCategory.isVisible());
        category = categoryRepository.save(category);
        return CategoryMapper.INSTANCE.mapCategoryDto(category);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("delete category with id {}", id);
        Category category = categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        categoryRepository.delete(category);
    }

    @Override
    @Transactional
    public CategoryDto changeVisibility(Long id) {
        log.info("change category visibility with id {}", id);
        Category category = categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        boolean visibility = !category.isVisible();
        category.setVisible(visibility);
        for (Dish dish : category.getDishes()) {
            dish.setVisible(visibility);
        }
        categoryRepository.save(category);
        return CategoryMapper.INSTANCE.mapCategoryDto(category);
    }
}
