package com.epam.spring.homework3.dto;

import com.epam.spring.homework3.model.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DishDto {

    private Long id;
    private String name;
    private String description;
    private Category category;
    private int price;
    private int weight;

}