package com.epam.spring.homework3.controller.assembler;

import com.epam.spring.homework3.controller.CategoryController;
import com.epam.spring.homework3.controller.model.CategoryModel;
import com.epam.spring.homework3.dto.CategoryDto;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CategoryAssembler extends RepresentationModelAssemblerSupport<CategoryDto, CategoryModel> {

    public static final String GET_ALL_REL = "get_all_categories";
    public static final String GET_REL = "get_category";
    public static final String CREATE_REL = "create_category";
    public static final String UPDATE_REL = "update_category";
    public static final String DELETE_REL = "delete_category";

    public CategoryAssembler() {
        super(CategoryController.class, CategoryModel.class);
    }

    @Override
    public CategoryModel toModel(CategoryDto entity) {
        CategoryModel categoryModel = new CategoryModel(entity);

        Link getAll = linkTo(methodOn(CategoryController.class).getAllCategories()).withRel(GET_ALL_REL);
        Link get = linkTo(methodOn(CategoryController.class).getCategory(entity.getId())).withRel(GET_REL);
        Link create = linkTo(methodOn(CategoryController.class).createCategory(entity)).withRel(CREATE_REL);
        Link update = linkTo(methodOn(CategoryController.class).updateCategory(entity.getId(), entity))
                .withRel(UPDATE_REL);
        Link delete = linkTo(methodOn(CategoryController.class).deleteCategory(entity.getId()))
                .withRel(DELETE_REL);

        categoryModel.add(getAll, get, create, update, delete);

        return categoryModel;
    }

}
