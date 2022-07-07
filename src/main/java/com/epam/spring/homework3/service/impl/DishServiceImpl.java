package com.epam.spring.homework3.service.impl;

import com.epam.spring.homework3.dto.DishDto;
import com.epam.spring.homework3.model.Dish;
import com.epam.spring.homework3.repository.DishRepository;
import com.epam.spring.homework3.service.DishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;

    @Override
    public List<DishDto> findAll() {
        log.info("find all dishes");

        List<Dish> dishes = dishRepository.findAll();
        List<DishDto> toReturn = new ArrayList<>();

        DishDto target;
        for (Dish source : dishes) {
            target = new DishDto();
            BeanUtils.copyProperties(source, target);
            toReturn.add(target);
        }
        return toReturn;
    }

    @Override
    public DishDto findById(Long id) {
        log.info("find dish with id {}", id);
        Dish source = dishRepository.findById(id);
        DishDto target = new DishDto();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    @Override
    public DishDto save(DishDto dishDto) {
        log.info("save dish with id {}", dishDto.getId());
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDto, dish);
        dish = dishRepository.save(dish);
        BeanUtils.copyProperties(dish, dishDto);
        return dishDto;
    }

    @Override
    public DishDto update(Long id, DishDto dishDto) {
        log.info("update dish with id {}", id);
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDto, dish);
        dish = dishRepository.update(id, dish);
        BeanUtils.copyProperties(dish, dishDto);
        return dishDto;
    }

    @Override
    public void deleteById(Long id) {
        log.info("delete dish with id {}", id);
        dishRepository.deleteById(id);
    }
}
