package com.epam.spring.homework3.service.impl;

import com.epam.spring.homework3.dto.CategoryDto;
import com.epam.spring.homework3.mapper.CategoryMapper;
import com.epam.spring.homework3.model.Category;
import com.epam.spring.homework3.repository.CategoryRepository;
import com.epam.spring.homework3.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        Category category = categoryRepository.findById(id);
        return CategoryMapper.INSTANCE.mapCategoryDto(category);
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        log.info("save category");
        Category category = categoryRepository.save(CategoryMapper.INSTANCE.mapCategory(categoryDto));
        return CategoryMapper.INSTANCE.mapCategoryDto(category);
    }

    @Override
    public CategoryDto update(Long id, CategoryDto categoryDto) {
        log.info("update category with id {}", id);
        Category category = categoryRepository.update(id,
                CategoryMapper.INSTANCE.mapCategory(categoryDto));
        return CategoryMapper.INSTANCE.mapCategoryDto(category);
    }

    @Override
    public void deleteById(Long id) {
        log.info("delete category with id {}", id);
        categoryRepository.deleteById(id);
    }
}
