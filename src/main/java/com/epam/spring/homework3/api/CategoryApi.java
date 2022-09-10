package com.epam.spring.homework3.api;

import com.epam.spring.homework3.controller.model.CategoryModel;
import com.epam.spring.homework3.dto.CategoryDto;
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

@Api(tags = "Category management api")
@RequestMapping("/api/v1/categories")
public interface CategoryApi {

    @ApiOperation("Get all categories")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<CategoryModel> getAllCategories();

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Category id")
    })
    @ApiOperation("Get category by id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    CategoryModel getCategory(@PathVariable Long id);

    @ApiOperation("Create category")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    CategoryModel createCategory(@RequestBody @Validated(OnCreate.class) CategoryDto categoryDto);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Category id")
    })
    @ApiOperation("Update category")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}")
    CategoryModel updateCategory(@PathVariable Long id,
                                 @RequestBody @Validated(OnUpdate.class) CategoryDto categoryDto);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Category id")
    })
    @ApiOperation("Delete category")
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> deleteCategory(@PathVariable Long id);

}
