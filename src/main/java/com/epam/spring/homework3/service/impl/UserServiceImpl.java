package com.epam.spring.homework3.service.impl;

import com.epam.spring.homework3.dto.UserDto;
import com.epam.spring.homework3.model.Dish;
import com.epam.spring.homework3.model.User;
import com.epam.spring.homework3.repository.DishRepository;
import com.epam.spring.homework3.repository.UserRepository;
import com.epam.spring.homework3.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DishRepository dishRepository;

    @Override
    public List<UserDto> findAll() {
        log.info("find all users");

        List<User> users = userRepository.findAll();
        List<UserDto> toReturn = new ArrayList<>();

        UserDto target;
        for (User source : users) {
            target = new UserDto();
            BeanUtils.copyProperties(source, target);
            toReturn.add(target);
        }
        return toReturn;
    }

    @Override
    public UserDto findById(Long id) {
        log.info("find user with id {}", id);
        User source = userRepository.findById(id);
        UserDto target = new UserDto();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    @Override
    public UserDto save(UserDto userDto) {
        log.info("save user with id {}", userDto.getId());
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user = userRepository.save(user);
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    @Override
    public UserDto update(Long id, UserDto userDto) {
        log.info("update user with id {}", id);
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user = userRepository.update(id, user);
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    @Override
    public void deleteById(Long id) {
        log.info("delete user with id {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public UserDto addDishToCart(Long userId, Long dishId) {
        log.info("adding dish {} to user {} cart", dishId, userId);
        Dish dish = dishRepository.findById(dishId);
        User user = userRepository.findById(userId);
        UserDto userDto = null;

        if (user != null && dish != null) {
            userDto = new UserDto();
            user.getCart().add(dish);
            BeanUtils.copyProperties(user, userDto);
        }
        return userDto;
    }

    @Override
    public UserDto removeDishFromCart(Long userId, Long dishId) {
        log.info("removing dish {} to user {} cart", dishId, userId);
        Dish dish = dishRepository.findById(dishId);
        User user = userRepository.findById(userId);
        UserDto userDto = null;

        if (user != null && dish != null) {
            userDto = new UserDto();
            user.getCart().remove(dish);
            BeanUtils.copyProperties(user, userDto);
        }

        return userDto;
    }
}
