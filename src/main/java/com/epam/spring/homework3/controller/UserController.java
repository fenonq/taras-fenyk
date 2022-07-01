package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.dto.UserDto;
import com.epam.spring.homework3.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        log.info("find all users");
        return userService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/user/{id}")
    public UserDto getUser(@PathVariable Long id) {
        log.info("find user with id {}", id);
        return userService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/user")
    public UserDto createUser(@RequestBody UserDto userDto) {
        log.info("save user");
        return userService.save(userDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/user/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        log.info("update user with id {}", id);
        return userService.update(id, userDto);
    }

    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long id) {
        log.info("delete user with id {}", id);
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/user/{userId}/cart/{dishId}")
    public UserDto addDishToCart(@PathVariable Long userId, @PathVariable Long dishId) {
        log.info("adding dish {} to user {} cart", dishId, userId);
        return userService.addDishToCart(userId, dishId);
    }

    @DeleteMapping(value = "/user/{userId}/cart/{dishId}")
    public UserDto removeDishFromCart(@PathVariable Long userId, @PathVariable Long dishId) {
        log.info("removing dish {} to user {} cart", dishId, userId);
        return userService.removeDishFromCart(userId, dishId);
    }
}
