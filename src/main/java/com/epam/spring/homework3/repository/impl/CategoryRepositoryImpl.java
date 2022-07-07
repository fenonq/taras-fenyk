package com.epam.spring.homework3.repository.impl;

import com.epam.spring.homework3.exception.EntityNotFoundException;
import com.epam.spring.homework3.model.Category;
import com.epam.spring.homework3.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CategoryRepositoryImpl implements CategoryRepository {

    private static Long id = 0L;
    private final List<Category> list = new ArrayList<>();

    @Override
    public List<Category> findAll() {
        log.info("find all categories");
        return new ArrayList<>(list);
    }

    @Override
    public Category findById(Long id) {
        log.info("find category with id {}", id);
        return list.stream()
                .filter(category -> category.getId().equals(id))
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Category save(Category category) {
        category.setId(++id);
        log.info("save category with id {}", category.getId());
        list.add(category);
        return category;
    }

    @Override
    public Category update(Long id, Category category) {
        log.info("update category with id {}", id);
        boolean isDeleted = list.removeIf(c -> c.getId().equals(id));
        if (isDeleted) {
            list.add(category);
        } else {
            throw new EntityNotFoundException();
        }
        return category;
    }

    @Override
    public void deleteById(Long id) {
        log.info("delete category with id {}", id);
        list.removeIf(category -> category.getId().equals(id));
    }
}
