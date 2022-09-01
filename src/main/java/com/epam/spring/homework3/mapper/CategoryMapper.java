package com.epam.spring.homework3.mapper;

import com.epam.spring.homework3.dto.CategoryDto;
import com.epam.spring.homework3.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category mapCategory(CategoryDto categoryDto);

    CategoryDto mapCategoryDto(Category category);

}
