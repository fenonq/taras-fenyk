package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.dto.DishDto;
import com.epam.spring.homework3.service.DishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/dishes")
    public List<DishDto> getAllDishes() {
        log.info("find all dishes");
        return dishService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/dish/{id}")
    public DishDto getDish(@PathVariable Long id) {
        log.info("find dish with id {}", id);
        return dishService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/dish")
    public DishDto createDish(@RequestBody DishDto dishDto) {
        log.info("save dish");
        return dishService.save(dishDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/dish/{id}")
    public DishDto updateDish(@PathVariable Long id, @RequestBody DishDto dishDto) {
        log.info("update dish with id {}", id);
        return dishService.update(id, dishDto);
    }

    @DeleteMapping(value = "/dish/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long id) {
        log.info("delete dish with id {}", id);
        dishService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
