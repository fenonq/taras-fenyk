package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.api.DishApi;
import com.epam.spring.homework3.controller.assembler.DishAssembler;
import com.epam.spring.homework3.controller.model.DishModel;
import com.epam.spring.homework3.dto.DishDto;
import com.epam.spring.homework3.service.DishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DishController implements DishApi {

    private final DishService dishService;
    private final DishAssembler dishAssembler;

    @Override
    public List<DishModel> getAllDishes() {
        log.info("find all dishes");
        List<DishDto> outDishDtoList = dishService.findAll();
        return outDishDtoList.stream().map(dishAssembler::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public DishModel getDish(Long id) {
        log.info("find dish with id {}", id);
        DishDto outDishDto = dishService.findById(id);
        return dishAssembler.toModel(outDishDto);
    }

    @Override
    public DishModel createDish(DishDto dishDto) {
        log.info("save dish");
        DishDto outDishDto = dishService.save(dishDto);
        return dishAssembler.toModel(outDishDto);
    }

    @Override
    public DishModel updateDish(Long id, DishDto dishDto) {
        log.info("update dish with id {}", id);
        DishDto outDishDto = dishService.update(id, dishDto);
        return dishAssembler.toModel(outDishDto);
    }

    @Override
    public ResponseEntity<Void> deleteDish(Long id) {
        log.info("delete dish with id {}", id);
        dishService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
