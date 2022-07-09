package com.epam.spring.homework3.controller.assembler;

import com.epam.spring.homework3.controller.UserController;
import com.epam.spring.homework3.controller.model.UserModel;
import com.epam.spring.homework3.dto.UserDto;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserAssembler extends RepresentationModelAssemblerSupport<UserDto, UserModel> {

    public static final String GET_ALL_REL = "get_all_users";
    public static final String GET_REL = "get_user";
    public static final String CREATE_REL = "create_user";
    public static final String UPDATE_REL = "update_user";
    public static final String DELETE_REL = "delete_user";
    public static final String CART_ADD_DISH_REL = "cart_add_dish";
    public static final String CART_REMOVE_DISH_REL = "cart_remove_dish";

    public UserAssembler() {
        super(UserController.class, UserModel.class);
    }

    @Override
    public UserModel toModel(UserDto entity) {
        UserModel userModel = new UserModel(entity);

        Link getAll = linkTo(methodOn(UserController.class).getAllUsers()).withRel(GET_ALL_REL);
        Link get = linkTo(methodOn(UserController.class).getUser(entity.getId())).withRel(GET_REL);
        Link create = linkTo(methodOn(UserController.class).createUser(entity)).withRel(CREATE_REL);
        Link update = linkTo(methodOn(UserController.class).updateUser(entity.getId(), entity))
                .withRel(UPDATE_REL);
        Link delete = linkTo(methodOn(UserController.class).deleteUser(entity.getId()))
                .withRel(DELETE_REL);
        Link addDish = linkTo(methodOn(UserController.class).addDishToCart(entity.getId(),
                0L)).withRel(CART_ADD_DISH_REL);
        Link removeDish = linkTo(methodOn(UserController.class).addDishToCart(entity.getId(),
                0L)).withRel(CART_REMOVE_DISH_REL);

        userModel.add(getAll, get, create, update, delete, addDish, removeDish);

        return userModel;
    }

}
