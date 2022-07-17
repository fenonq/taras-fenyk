package com.epam.spring.homework3.service;

import com.epam.spring.homework3.dto.UserDto;

public interface UserService extends CrudService<UserDto, Long> {

    UserDto findUserByUsername(String username);

    UserDto addDishToCart(Long userId, Long dishId);

    UserDto removeDishFromCart(Long userId, Long dishId);

    UserDto changeActive(Long userId);

}
