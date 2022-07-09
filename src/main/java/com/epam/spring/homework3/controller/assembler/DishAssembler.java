package com.epam.spring.homework3.controller.assembler;

import com.epam.spring.homework3.controller.DishController;
import com.epam.spring.homework3.controller.model.DishModel;
import com.epam.spring.homework3.dto.DishDto;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class DishAssembler extends RepresentationModelAssemblerSupport<DishDto, DishModel> {

    public static final String GET_ALL_REL = "get_all_dishes";
    public static final String GET_REL = "get_dish";
    public static final String CREATE_REL = "create_dish";
    public static final String UPDATE_REL = "update_dish";
    public static final String DELETE_REL = "delete_dish";

    public DishAssembler() {
        super(DishController.class, DishModel.class);
    }

    @Override
    public DishModel toModel(DishDto entity) {
        DishModel dishModel = new DishModel(entity);

        Link getAll = linkTo(methodOn(DishController.class).getAllDishes()).withRel(GET_ALL_REL);
        Link get = linkTo(methodOn(DishController.class).getDish(entity.getId())).withRel(GET_REL);
        Link create = linkTo(methodOn(DishController.class).createDish(entity)).withRel(CREATE_REL);
        Link update = linkTo(methodOn(DishController.class).updateDish(entity.getId(), entity))
                .withRel(UPDATE_REL);
        Link delete = linkTo(methodOn(DishController.class).deleteDish(entity.getId()))
                .withRel(DELETE_REL);

        dishModel.add(getAll, get, create, update, delete);

        return dishModel;
    }

}
