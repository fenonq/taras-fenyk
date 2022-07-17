package com.epam.spring.homework3.service.impl;

import com.epam.spring.homework3.dto.UserDto;
import com.epam.spring.homework3.mapper.UserMapper;
import com.epam.spring.homework3.model.Dish;
import com.epam.spring.homework3.model.User;
import com.epam.spring.homework3.repository.DishRepository;
import com.epam.spring.homework3.repository.UserRepository;
import com.epam.spring.homework3.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DishRepository dishRepository;

    @Override
    public List<UserDto> findAll() {
        log.info("find all users");
        return userRepository.findAll()
                .stream()
                .map(UserMapper.INSTANCE::mapUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Long id) {
        log.info("find user with id {}", id);
        User user = userRepository.findById(id);
        return UserMapper.INSTANCE.mapUserDto(user);
    }

    @Override
    public UserDto save(UserDto userDto) {
        log.info("save user");
        User user = userRepository.save(UserMapper.INSTANCE.mapUser(userDto));
        return UserMapper.INSTANCE.mapUserDto(user);
    }

    @Override
    public UserDto update(Long id, UserDto userDto) {
        log.info("update user with id {}", id);
        User user = userRepository.findById(id);
        user = userRepository.update(id,
                UserMapper.INSTANCE.populateUserWithPresentUserDtoFields(user, userDto));
        return UserMapper.INSTANCE.mapUserDto(user);
    }

    @Override
    public void deleteById(Long id) {
        log.info("delete user with id {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public UserDto findUserByUsername(String username) {
        log.info("find user with username {}", username);
        User user = userRepository.findUserByUsername(username);
        return UserMapper.INSTANCE.mapUserDto(user);
    }

    @Override
    public UserDto addDishToCart(Long userId, Long dishId) {
        log.info("adding dish {} to user {} cart", dishId, userId);
        Dish dish = dishRepository.findById(dishId);
        User user = userRepository.findById(userId);

        if (user != null && dish != null) {
            user.getCart().add(dish);
        }
        return UserMapper.INSTANCE.mapUserDto(user);
    }

    @Override
    public UserDto removeDishFromCart(Long userId, Long dishId) {
        log.info("removing dish {} to user {} cart", dishId, userId);
        Dish dish = dishRepository.findById(dishId);
        User user = userRepository.findById(userId);

        if (user != null && dish != null) {
            user.getCart().remove(dish);
        }
        return UserMapper.INSTANCE.mapUserDto(user);
    }

//    private User populateUserWithPresentUserDtoFields(User user, UserDto userDto) {
//        String firstName = userDto.getFirstName();
//        if (Objects.nonNull(firstName)) {
//            user.setFirstName(firstName);
//        }
//        String lastName = userDto.getLastName();
//        if (Objects.nonNull(lastName)) {
//            user.setLastName(lastName);
//        }
//        return user;
//    }
}
