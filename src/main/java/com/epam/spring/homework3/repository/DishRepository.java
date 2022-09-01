package com.epam.spring.homework3.repository;

import com.epam.spring.homework3.model.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Long> {

    Page<Dish> findAll(Pageable pageable);

}
