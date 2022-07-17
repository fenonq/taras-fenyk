package com.epam.spring.homework3.api;

import com.epam.spring.homework3.controller.model.UserModel;
import com.epam.spring.homework3.dto.UserDto;
import com.epam.spring.homework3.dto.group.OnCreate;
import com.epam.spring.homework3.dto.group.OnUpdate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "User management api")
@RequestMapping("/api/v1/users")
public interface UserApi {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<UserModel> getAllUsers();

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "User id")
    })
    @ApiOperation("Get user by id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    UserModel getUser(@PathVariable Long id);

    @ApiOperation("Create user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    UserModel createUser(@RequestBody @Validated(OnCreate.class) UserDto userDto);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "User id")
    })
    @ApiOperation("Update user firstName and lastName")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}")
    UserModel updateUser(@PathVariable Long id, @RequestBody @Validated(OnUpdate.class) UserDto userDto);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", paramType = "path", required = true, value = "User id"),
            @ApiImplicitParam(name = "dishId", paramType = "path", required = true, value = "Dish id")
    })
    @ApiOperation("Add dish to user cart")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/{userId}/cart/add/{dishId}")
    UserModel addDishToCart(@PathVariable Long userId, @PathVariable Long dishId);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", paramType = "path", required = true, value = "User id"),
            @ApiImplicitParam(name = "dishId", paramType = "path", required = true, value = "Dish id")
    })
    @ApiOperation("Remove dish to user cart")
    @PatchMapping(value = "/{userId}/cart/remove/{dishId}")
    UserModel removeDishFromCart(@PathVariable Long userId, @PathVariable Long dishId);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "User id")
    })
    @ApiOperation("Ban/Unban user by id")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/ban/{id}")
    UserModel banUser(@PathVariable Long id);

}
