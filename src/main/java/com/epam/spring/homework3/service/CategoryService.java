package com.epam.spring.homework3.service;

import com.epam.spring.homework3.dto.CategoryDto;

public interface CategoryService extends CrudService<CategoryDto, Long> {

    CategoryDto changeVisibility(Long id);

}
