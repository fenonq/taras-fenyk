package com.epam.spring.homework3.api;

import com.epam.spring.homework3.controller.model.DishModel;
import com.epam.spring.homework3.dto.DishDto;
import com.epam.spring.homework3.dto.group.OnCreate;
import com.epam.spring.homework3.dto.group.OnUpdate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Dish management api")
@RequestMapping("/api/v1/dishes")
public interface DishApi {

    @ApiOperation("Get all dishes")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<DishModel> getAllDishes();

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Dish id")
    })
    @ApiOperation("Get dish by id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    DishModel getDish(@PathVariable Long id);

    @ApiOperation("Create dish")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    DishModel createDish(@RequestBody @Validated(OnCreate.class) DishDto dishDto);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Dish id")
    })
    @ApiOperation("Update dish")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}")
    DishModel updateDish(@PathVariable Long id, @RequestBody @Validated(OnUpdate.class) DishDto dishDto);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Dish id")
    })
    @ApiOperation("Delete dish")
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> deleteDish(@PathVariable Long id);

}
