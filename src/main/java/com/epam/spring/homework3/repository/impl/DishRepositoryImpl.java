package com.epam.spring.homework3.repository.impl;

import com.epam.spring.homework3.model.Dish;
import com.epam.spring.homework3.repository.DishRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class DishRepositoryImpl implements DishRepository {

    private final List<Dish> list = new ArrayList<>();

    @Override
    public List<Dish> findAll() {
        log.info("find all dishes");
        return new ArrayList<>(list);
    }

    @Override
    public Dish findById(Long id) {
        log.info("find dish with id {}", id);
        return list.stream()
                .filter(dish -> dish.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Dish is not found!"));
    }

    @Override
    public Dish save(Dish dish) {
        log.info("save dish with id {}", dish.getId());
        list.add(dish);
        return dish;
    }

    @Override
    public Dish update(Long id, Dish dish) {
        log.info("update dish with id {}", id);
        boolean isDeleted = list.removeIf(d -> d.getId().equals(id));
        if (isDeleted) {
            list.add(dish);
        } else {
            throw new RuntimeException("Dish is not found!");
        }
        return dish;
    }

    @Override
    public void deleteById(Long id) {
        log.info("delete dish with id {}", id);
        list.removeIf(dish -> dish.getId().equals(id));
    }
}
