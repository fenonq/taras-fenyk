package com.epam.spring.homework3.service.impl;

import com.epam.spring.homework3.dto.CategoryDto;
import com.epam.spring.homework3.model.Category;
import com.epam.spring.homework3.repository.CategoryRepository;
import com.epam.spring.homework3.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> findAll() {
        log.info("find all categories");

        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> toReturn = new ArrayList<>();

        CategoryDto target;
        for (Category source : categories) {
            target = new CategoryDto();
            BeanUtils.copyProperties(source, target);
            toReturn.add(target);
        }
        return toReturn;
    }

    @Override
    public CategoryDto findById(Long id) {
        log.info("find category with id {}", id);
        Category target = categoryRepository.findById(id);
        CategoryDto source = new CategoryDto();
        BeanUtils.copyProperties(target, source);
        return source;
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        log.info("save category with id {}", categoryDto.getId());
        Category category = new Category();
        BeanUtils.copyProperties(categoryDto, category);
        category = categoryRepository.save(category);
        BeanUtils.copyProperties(category, categoryDto);
        return categoryDto;
    }

    @Override
    public CategoryDto update(Long id, CategoryDto categoryDto) {
        log.info("update category with id {}", id);
        Category category = new Category();
        BeanUtils.copyProperties(categoryDto, category);
        category = categoryRepository.update(id, category);
        BeanUtils.copyProperties(category, categoryDto);
        return categoryDto;
    }

    @Override
    public void deleteById(Long id) {
        log.info("delete category with id {}", id);
        categoryRepository.deleteById(id);
    }
}
