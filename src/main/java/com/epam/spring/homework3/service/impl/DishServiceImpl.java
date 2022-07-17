package com.epam.spring.homework3.service.impl;

import com.epam.spring.homework3.dto.DishDto;
import com.epam.spring.homework3.exception.EntityNotFoundException;
import com.epam.spring.homework3.mapper.DishMapper;
import com.epam.spring.homework3.model.Dish;
import com.epam.spring.homework3.repository.DishRepository;
import com.epam.spring.homework3.service.DishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;

    @Override
    public List<DishDto> findAll() {
        log.info("find all dishes");
        return dishRepository.findAll()
                .stream()
                .map(DishMapper.INSTANCE::mapDishDto)
                .collect(Collectors.toList());
    }

    @Override
    public DishDto findById(Long id) {
        log.info("find dish with id {}", id);
        Dish dish = dishRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return DishMapper.INSTANCE.mapDishDto(dish);
    }

    @Override
    public DishDto save(DishDto dishDto) {
        log.info("save dish");
        Dish dish = DishMapper.INSTANCE.mapDish(dishDto);
        dish.setVisible(true);
        dish = dishRepository.save(dish);
        return DishMapper.INSTANCE.mapDishDto(dish);
    }

    @Override
    @Transactional
    public DishDto update(Long id, DishDto dishDto) {
        log.info("update dish with id {}", id);
        Dish existingDish = dishRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Dish dish = DishMapper.INSTANCE.mapDish(dishDto);
        dish.setVisible(existingDish.isVisible());
        dish = dishRepository.save(dish);
        return DishMapper.INSTANCE.mapDishDto(dish);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("delete dish with id {}", id);
        Dish dish = dishRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        dishRepository.delete(dish);
    }

    @Override
    public Page<DishDto> findAll(Pageable pageable) {
        Page<Dish> pagedResult = dishRepository.findAll(pageable);
        return new PageImpl<>(pagedResult.getContent().stream().map(DishMapper.INSTANCE::mapDishDto)
                .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public DishDto changeVisibility(Long id) {
        Dish dish = dishRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        dish.setVisible(!dish.isVisible());
        dishRepository.save(dish);
        return DishMapper.INSTANCE.mapDishDto(dish);
    }
}
