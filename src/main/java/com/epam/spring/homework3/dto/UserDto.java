package com.epam.spring.homework3.dto;

import com.epam.spring.homework3.model.Dish;
import com.epam.spring.homework3.model.Roles;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private Roles role;
    private List<Dish> cart = new ArrayList<>();
    
}
