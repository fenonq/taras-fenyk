package com.epam.spring.homework3.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class User {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private Roles role;
    private List<Dish> cart = new ArrayList<>();

}
