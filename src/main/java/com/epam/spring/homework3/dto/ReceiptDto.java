package com.epam.spring.homework3.dto;

import com.epam.spring.homework3.model.Dish;
import com.epam.spring.homework3.model.Status;
import com.epam.spring.homework3.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ReceiptDto {

    private Long id;
    private User customer;
    private User manager;
    private Status status;
    private Integer totalPrice;
    private LocalDateTime createDate;
    private List<Dish> dishes = new ArrayList<>();
    
}
