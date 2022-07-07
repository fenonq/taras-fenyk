package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.api.DishApi;
import com.epam.spring.homework3.dto.DishDto;
import com.epam.spring.homework3.service.DishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DishController implements DishApi {

    private final DishService dishService;

    @Override
    public List<DishDto> getAllDishes() {
        log.info("find all dishes");
        return dishService.findAll();
    }

    @Override
    public DishDto getDish(Long id) {
        log.info("find dish with id {}", id);
        return dishService.findById(id);
    }

    @Override
    public DishDto createDish(DishDto dishDto) {
        log.info("save dish");
        return dishService.save(dishDto);
    }

    @Override
    public DishDto updateDish(Long id, DishDto dishDto) {
        log.info("update dish with id {}", id);
        return dishService.update(id, dishDto);
    }

    @Override
    public ResponseEntity<Void> deleteDish(Long id) {
        log.info("delete dish with id {}", id);
        dishService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
