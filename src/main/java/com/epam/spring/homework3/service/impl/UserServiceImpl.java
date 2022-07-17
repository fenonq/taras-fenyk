package com.epam.spring.homework3.service.impl;

import com.epam.spring.homework3.dto.UserDto;
import com.epam.spring.homework3.exception.EntityNotFoundException;
import com.epam.spring.homework3.mapper.UserMapper;
import com.epam.spring.homework3.model.Dish;
import com.epam.spring.homework3.model.User;
import com.epam.spring.homework3.repository.DishRepository;
import com.epam.spring.homework3.repository.UserRepository;
import com.epam.spring.homework3.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
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
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return UserMapper.INSTANCE.mapUserDto(user);
    }

    @Override
    public UserDto save(UserDto userDto) {
        log.info("save user");
        User user = UserMapper.INSTANCE.mapUser(userDto);
        user.setActive(true);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user = userRepository.save(user);
        return UserMapper.INSTANCE.mapUserDto(user);
    }

    @Override
    @Transactional
    public UserDto update(Long id, UserDto userDto) {
        log.info("update user with id {}", id);
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        user = userRepository.save(UserMapper.INSTANCE.populateUserWithPresentUserDtoFields(user, userDto));
        return UserMapper.INSTANCE.mapUserDto(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("delete user with id {}", id);
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        userRepository.delete(user);
    }

    @Override
    public UserDto findUserByUsername(String username) {
        log.info("find user with username {}", username);
        User user = userRepository.findByUsername(username);
        return UserMapper.INSTANCE.mapUserDto(user);
    }

    @Override
    @Transactional
    public UserDto addDishToCart(Long userId, Long dishId) {
        log.info("adding dish {} to user {} cart", dishId, userId);
        Dish dish = dishRepository.findById(dishId).orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);

        if (user != null && dish != null) {
            user.getCart().add(dish);
            user = userRepository.save(user);
        }
        return UserMapper.INSTANCE.mapUserDto(user);
    }

    @Override
    @Transactional
    public UserDto removeDishFromCart(Long userId, Long dishId) {
        log.info("removing dish {} to user {} cart", dishId, userId);
        Dish dish = dishRepository.findById(dishId).orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);

        if (user != null && dish != null) {
            user.getCart().remove(dish);
            user = userRepository.save(user);
        }
        return UserMapper.INSTANCE.mapUserDto(user);
    }

    @Override
    @Transactional
    public UserDto changeActive(Long userId) {
        log.info("ban/unban user with id {}", userId);
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        userRepository.changUserStatus(userId, !user.getActive());
        user.setActive(!user.getActive());
        return UserMapper.INSTANCE.mapUserDto(user);
    }
}
