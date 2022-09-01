package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.controller.assembler.CategoryAssembler;
import com.epam.spring.homework3.controller.model.CategoryModel;
import com.epam.spring.homework3.dto.CategoryDto;
import com.epam.spring.homework3.model.enums.ErrorType;
import com.epam.spring.homework3.service.CategoryService;
import com.epam.spring.homework3.test.config.TestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.epam.spring.homework3.test.util.TestDataUtil.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CategoryController.class)
@Import(TestConfig.class)
class CategoryControllerTest {

    public static final String CATEGORIES_URL = "/api/v1/categories";

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private CategoryAssembler categoryAssembler;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllCategoriesTest() throws Exception {
        CategoryDto categoryDto = createCategoryDto();
        CategoryModel categoryModel = new CategoryModel(categoryDto);

        when(categoryService.findAll()).thenReturn(Collections.singletonList(categoryDto));
        when(categoryAssembler.toModel(categoryDto)).thenReturn(categoryModel);

        mockMvc.perform(get(CATEGORIES_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value(categoryDto.getName()));
        verify(categoryService).findAll();
    }

    @Test
    void getCategoryTest() throws Exception {
        CategoryDto categoryDto = createCategoryDto();
        CategoryModel categoryModel = new CategoryModel(categoryDto);

        when(categoryService.findById(anyLong())).thenReturn(categoryDto);
        when(categoryAssembler.toModel(categoryDto)).thenReturn(categoryModel);

        mockMvc.perform(get(CATEGORIES_URL + "/" + ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.name").value(categoryDto.getName()));
        verify(categoryService).findById(anyLong());
    }

    @Test
    void createCategoryTest() throws Exception {
        CategoryDto categoryDto = createCategoryDto();
        categoryDto.setId(null);
        categoryDto.setVisible(null);
        CategoryModel categoryModel = new CategoryModel(categoryDto);

        when(categoryService.save(any(CategoryDto.class))).thenReturn(categoryDto);
        when(categoryAssembler.toModel(categoryDto)).thenReturn(categoryModel);

        mockMvc.perform(post(CATEGORIES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(categoryDto.getName()));
        verify(categoryService).save(any(CategoryDto.class));
    }

    @Test
    void createNotValidCategoryTest() throws Exception {
        CategoryDto categoryDto = CategoryDto.builder().id(ID).build();
        CategoryModel categoryModel = new CategoryModel(categoryDto);

        when(categoryService.save(any(CategoryDto.class))).thenReturn(categoryDto);
        when(categoryAssembler.toModel(categoryDto)).thenReturn(categoryModel);

        mockMvc.perform(post(CATEGORIES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[1].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()));
    }

    @Test
    void updateCategoryTest() throws Exception {
        CategoryDto categoryDto = createCategoryDto();
        categoryDto.setVisible(null);
        CategoryModel categoryModel = new CategoryModel(categoryDto);

        when(categoryService.update(anyLong(), any(CategoryDto.class))).thenReturn(categoryDto);
        when(categoryAssembler.toModel(categoryDto)).thenReturn(categoryModel);

        mockMvc.perform(put(CATEGORIES_URL + "/" + ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(categoryDto.getName()));
        verify(categoryService).update(anyLong(), any(CategoryDto.class));
    }

    @Test
    void updateNotValidCategoryTest() throws Exception {
        CategoryDto categoryDto = CategoryDto.builder().build();
        CategoryModel categoryModel = new CategoryModel(categoryDto);

        when(categoryService.update(anyLong(), any(CategoryDto.class))).thenReturn(categoryDto);
        when(categoryAssembler.toModel(categoryDto)).thenReturn(categoryModel);

        mockMvc.perform(put(CATEGORIES_URL + "/" + ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()));
    }

    @Test
    void changeVisibilityTest() throws Exception {
        CategoryDto categoryDto = createCategoryDto();
        CategoryModel categoryModel = new CategoryModel(categoryDto);

        when(categoryService.changeVisibility(anyLong())).thenReturn(categoryDto);
        when(categoryAssembler.toModel(categoryDto)).thenReturn(categoryModel);

        mockMvc.perform(patch(CATEGORIES_URL + "/visibility/" + ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(categoryDto.getName()));
        verify(categoryService).changeVisibility(anyLong());
    }

}
