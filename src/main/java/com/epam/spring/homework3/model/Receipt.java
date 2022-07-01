package com.epam.spring.homework3.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {

    private Long id;
    private User customer;
    private User manager;
    private Status status;
    private Integer totalPrice;
    private LocalDateTime createDate;
    private List<Dish> dishes = new ArrayList<>();

}

