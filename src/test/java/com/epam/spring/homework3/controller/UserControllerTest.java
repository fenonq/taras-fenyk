package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.controller.assembler.UserAssembler;
import com.epam.spring.homework3.controller.model.UserModel;
import com.epam.spring.homework3.dto.UserDto;
import com.epam.spring.homework3.exception.EntityNotFoundException;
import com.epam.spring.homework3.model.enums.ErrorType;
import com.epam.spring.homework3.service.UserService;
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
@WebMvcTest(UserController.class)
@Import(TestConfig.class)
class UserControllerTest {

    public static final String USERS_URL = "/api/v1/users";

    @MockBean
    private UserService userService;

    @MockBean
    private UserAssembler userAssembler;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllUsersTest() throws Exception {
        UserDto userDto = createUserDtoCustomer();
        UserModel userModel = new UserModel(userDto);

        when(userService.findAll()).thenReturn(Collections.singletonList(userDto));
        when(userAssembler.toModel(userDto)).thenReturn(userModel);

        mockMvc.perform(get(USERS_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].username").value(userDto.getUsername()));
        verify(userService).findAll();
    }

    @Test
    void getUserTest() throws Exception {
        UserDto userDto = createUserDtoCustomer();
        UserModel userModel = new UserModel(userDto);

        when(userService.findById(anyLong())).thenReturn(userDto);
        when(userAssembler.toModel(userDto)).thenReturn(userModel);

        mockMvc.perform(get(USERS_URL + "/" + ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.username").value(userDto.getUsername()));
        verify(userService).findById(anyLong());
    }

    @Test
    void getUserNotFoundTest() throws Exception {
        when(userService.findById(anyLong())).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get(USERS_URL + "/" + ID))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()));
        verify(userService).findById(anyLong());
    }

    @Test
    void fatalErrorTest() throws Exception {
        when(userService.findById(anyLong())).thenThrow(new RuntimeException());

        mockMvc.perform(get(USERS_URL + "/" + ID))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorType").value(ErrorType.FATAL_ERROR_TYPE.name()));
    }

    @Test
    void createUserTest() throws Exception {
        UserDto userDto = createUserDtoCustomer();
        userDto.setId(null);
        userDto.setCart(null);
        userDto.setActive(null);
        UserModel userModel = new UserModel(userDto);

        when(userService.save(any(UserDto.class))).thenReturn(userDto);
        when(userAssembler.toModel(userDto)).thenReturn(userModel);

        mockMvc.perform(post(USERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(userDto.getUsername()));
        verify(userService).save(any(UserDto.class));
    }

    @Test
    void createNotValidUserTest() throws Exception {
        UserDto userDto = UserDto.builder().id(ID).build();
        UserModel userModel = new UserModel(userDto);

        when(userService.save(any(UserDto.class))).thenReturn(userDto);
        when(userAssembler.toModel(userDto)).thenReturn(userModel);

        mockMvc.perform(post(USERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
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
    void updateUserTest() throws Exception {
        UserDto userDto = UserDto.builder()
                .id(ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
        UserModel userModel = new UserModel(userDto);

        when(userService.update(anyLong(), any(UserDto.class))).thenReturn(userDto);
        when(userAssembler.toModel(userDto)).thenReturn(userModel);

        mockMvc.perform(put(USERS_URL + "/" + ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(userDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userDto.getLastName()));
        verify(userService).update(anyLong(), any(UserDto.class));
    }

    @Test
    void updateNotValidUserTest() throws Exception {
        UserDto userDto = createUserDtoCustomer();
        userDto.setId(null);
        userDto.setFirstName(null);
        userDto.setLastName(null);
        UserModel userModel = new UserModel(userDto);

        when(userService.update(anyLong(), any(UserDto.class))).thenReturn(userDto);
        when(userAssembler.toModel(userDto)).thenReturn(userModel);

        mockMvc.perform(put(USERS_URL + "/" + ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
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
    void addDishToCartTest() throws Exception {
        UserDto userDto = createUserDtoCustomer();
        UserModel userModel = new UserModel(userDto);

        when(userService.addDishToCart(anyLong(), anyLong())).thenReturn(userDto);
        when(userAssembler.toModel(userDto)).thenReturn(userModel);

        mockMvc.perform(patch(USERS_URL + "/" + ID + "/cart/add/" + ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(userDto.getUsername()));
        verify(userService).addDishToCart(anyLong(), anyLong());
    }

    @Test
    void removeDishFromCartTest() throws Exception {
        UserDto userDto = createUserDtoCustomer();
        UserModel userModel = new UserModel(userDto);

        when(userService.removeDishFromCart(anyLong(), anyLong())).thenReturn(userDto);
        when(userAssembler.toModel(userDto)).thenReturn(userModel);

        mockMvc.perform(patch(USERS_URL + "/" + ID + "/cart/remove/" + ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(userDto.getUsername()));
        verify(userService).removeDishFromCart(anyLong(), anyLong());
    }

    @Test
    void banUserTest() throws Exception {
        UserDto userDto = createUserDtoCustomer();
        UserModel userModel = new UserModel(userDto);

        when(userService.changeActive(anyLong())).thenReturn(userDto);
        when(userAssembler.toModel(userDto)).thenReturn(userModel);

        mockMvc.perform(patch(USERS_URL + "/ban/" + ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(userDto.getUsername()));
        verify(userService).changeActive(anyLong());
    }

}
