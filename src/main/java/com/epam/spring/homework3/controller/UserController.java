package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.api.UserApi;
import com.epam.spring.homework3.dto.UserDto;
import com.epam.spring.homework3.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public List<UserDto> getAllUsers() {
        log.info("find all users");
        return userService.findAll();
    }

    @Override
    public UserDto getUser(Long id) {
        log.info("find user with id {}", id);
        return userService.findById(id);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("save user");
        return userService.save(userDto);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        log.info("update user with id {}", id);
        return userService.update(id, userDto);
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        log.info("delete user with id {}", id);
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public UserDto addDishToCart(Long userId, Long dishId) {
        log.info("adding dish {} to user {} cart", dishId, userId);
        return userService.addDishToCart(userId, dishId);
    }

    @Override
    public UserDto removeDishFromCart(Long userId, Long dishId) {
        log.info("removing dish {} to user {} cart", dishId, userId);
        return userService.removeDishFromCart(userId, dishId);
    }

}
