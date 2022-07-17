package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.api.UserApi;
import com.epam.spring.homework3.controller.assembler.UserAssembler;
import com.epam.spring.homework3.controller.model.UserModel;
import com.epam.spring.homework3.dto.UserDto;
import com.epam.spring.homework3.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;
    private final UserAssembler userAssembler;

    @Override
    public List<UserModel> getAllUsers() {
        log.info("find all users");
        List<UserDto> outUserDtoList = userService.findAll();
        return outUserDtoList.stream().map(userAssembler::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public UserModel getUser(Long id) {
        log.info("find user with id {}", id);
        UserDto outUserDto = userService.findById(id);
        return userAssembler.toModel(outUserDto);
    }

    @Override
    public UserModel createUser(UserDto userDto) {
        log.info("save user");
        UserDto outUserDto = userService.save(userDto);
        return userAssembler.toModel(outUserDto);
    }

    @Override
    public UserModel updateUser(Long id, UserDto userDto) {
        log.info("update user with id {}", id);
        UserDto outUserDto = userService.update(id, userDto);
        return userAssembler.toModel(outUserDto);
    }

    @Override
    public UserModel addDishToCart(Long userId, Long dishId) {
        log.info("adding dish {} to user {} cart", dishId, userId);
        UserDto outUserDto = userService.addDishToCart(userId, dishId);
        return userAssembler.toModel(outUserDto);
    }

    @Override
    public UserModel removeDishFromCart(Long userId, Long dishId) {
        log.info("removing dish {} to user {} cart", dishId, userId);
        UserDto outUserDto = userService.removeDishFromCart(userId, dishId);
        return userAssembler.toModel(outUserDto);
    }

    @Override
    public UserModel banUser(Long id) {
        log.info("ban/unban user with id {}", id);
        UserDto outUserDto = userService.changeActive(id);
        return userAssembler.toModel(outUserDto);
    }

}
