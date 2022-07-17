package com.epam.spring.homework3.mapper;

import com.epam.spring.homework3.dto.DishDto;
import com.epam.spring.homework3.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DishMapper {

    DishMapper INSTANCE = Mappers.getMapper(DishMapper.class);

    Dish mapDish(DishDto dishDto);

    DishDto mapDishDto(Dish dish);

}
