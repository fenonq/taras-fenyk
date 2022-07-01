package com.epam.spring.homework3.repository.impl;

import com.epam.spring.homework3.model.User;
import com.epam.spring.homework3.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class UserRepositoryImpl implements UserRepository {

    private final List<User> list = new ArrayList<>();

    @Override
    public List<User> findAll() {
        log.info("find all users");
        return new ArrayList<>(list);
    }

    @Override
    public User findById(Long id) {
        log.info("find user with id {}", id);
        return list.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User is not found!"));
    }

    @Override
    public User save(User user) {
        log.info("save user with id {}", user.getId());
        list.add(user);
        return user;
    }

    @Override
    public User update(Long id, User user) {
        log.info("update user with id {}", id);
        boolean isDeleted = list.removeIf(u -> u.getId().equals(id));
        if (isDeleted) {
            list.add(user);
        } else {
            throw new RuntimeException("User is not found!");
        }
        return user;
    }

    @Override
    public void deleteById(Long id) {
        log.info("delete user with id {}", id);
        list.removeIf(user -> user.getId().equals(id));
    }
}
