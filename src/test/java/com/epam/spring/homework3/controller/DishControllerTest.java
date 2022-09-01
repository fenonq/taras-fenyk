package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.controller.assembler.DishAssembler;
import com.epam.spring.homework3.controller.model.DishModel;
import com.epam.spring.homework3.dto.DishDto;
import com.epam.spring.homework3.model.enums.ErrorType;
import com.epam.spring.homework3.service.DishService;
import com.epam.spring.homework3.test.config.TestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static com.epam.spring.homework3.test.util.TestDataUtil.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DishController.class)
@Import(TestConfig.class)
class DishControllerTest {

    public static final String DISHES_URL = "/api/v1/dishes";

    @MockBean
    private DishService dishService;

    @MockBean
    private DishAssembler dishAssembler;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllDishesTest() throws Exception {
        int page = 0;
        int size = 2;
        DishDto dishDto = createDishDto();
        DishModel dishModel = new DishModel(dishDto);
        List<DishDto> dishDtoList = Arrays.asList(dishDto, dishDto);
        Pageable pageable = PageRequest.of(page, size);

        Page<DishDto> dishDtoPage = new PageImpl<>(dishDtoList, pageable, dishDtoList.size());

        when(dishService.findAll(any(Pageable.class))).thenReturn(dishDtoPage);
        when(dishAssembler.toModel(dishDto)).thenReturn(dishModel);

        mockMvc.perform(get(DISHES_URL)
                        .queryParam("page", String.valueOf(page))
                        .queryParam("size", String.valueOf(size)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pageable.pageNumber").value(page))
                .andExpect(jsonPath("$.pageable.pageSize").value(size))
                .andExpect(jsonPath("$.content[0].name").value(dishDto.getName()));
        verify(dishService).findAll(any(Pageable.class));
    }

    @Test
    void getDishTest() throws Exception {
        DishDto dishDto = createDishDto();
        DishModel dishModel = new DishModel(dishDto);

        when(dishService.findById(anyLong())).thenReturn(dishDto);
        when(dishAssembler.toModel(dishDto)).thenReturn(dishModel);

        mockMvc.perform(get(DISHES_URL + "/" + ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.name").value(dishDto.getName()));
        verify(dishService).findById(anyLong());
    }

    @Test
    void createDishTest() throws Exception {
        DishDto dishDto = createDishDto();
        dishDto.setId(null);
        dishDto.setVisible(null);
        DishModel dishModel = new DishModel(dishDto);

        when(dishService.save(any(DishDto.class))).thenReturn(dishDto);
        when(dishAssembler.toModel(dishDto)).thenReturn(dishModel);

        mockMvc.perform(post(DISHES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dishDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(dishDto.getName()));
        verify(dishService).save(any(DishDto.class));
    }

    @Test
    void createNotValidDishTest() throws Exception {
        DishDto dishDto = DishDto.builder().id(ID).build();
        DishModel dishModel = new DishModel(dishDto);

        when(dishService.save(any(DishDto.class))).thenReturn(dishDto);
        when(dishAssembler.toModel(dishDto)).thenReturn(dishModel);

        mockMvc.perform(post(DISHES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dishDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        jsonPath("$[0].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()),
                        jsonPath("$[1].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()),
                        jsonPath("$[2].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()),
                        jsonPath("$[3].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()),
                        jsonPath("$[4].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()),
                        jsonPath("$[5].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())
                );
    }

    @Test
    void updateDishTest() throws Exception {
        DishDto dishDto = createDishDto();
        dishDto.setVisible(null);
        DishModel dishModel = new DishModel(dishDto);

        when(dishService.update(anyLong(), any(DishDto.class))).thenReturn(dishDto);
        when(dishAssembler.toModel(dishDto)).thenReturn(dishModel);

        mockMvc.perform(put(DISHES_URL + "/" + ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dishDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(dishDto.getName()));
        verify(dishService).update(anyLong(), any(DishDto.class));
    }

    @Test
    void updateNotValidDishTest() throws Exception {
        DishDto dishDto = DishDto.builder().build();
        DishModel dishModel = new DishModel(dishDto);

        when(dishService.update(anyLong(), any(DishDto.class))).thenReturn(dishDto);
        when(dishAssembler.toModel(dishDto)).thenReturn(dishModel);

        mockMvc.perform(put(DISHES_URL + "/" + ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dishDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        jsonPath("$[0].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()),
                        jsonPath("$[1].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()),
                        jsonPath("$[2].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name())
                );
    }

    @Test
    void changeVisibilityTest() throws Exception {
        DishDto dishDto = createDishDto();
        DishModel dishModel = new DishModel(dishDto);

        when(dishService.changeVisibility(anyLong())).thenReturn(dishDto);
        when(dishAssembler.toModel(dishDto)).thenReturn(dishModel);

        mockMvc.perform(patch(DISHES_URL + "/visibility/" + ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(dishDto.getName()));
        verify(dishService).changeVisibility(anyLong());
    }

}
