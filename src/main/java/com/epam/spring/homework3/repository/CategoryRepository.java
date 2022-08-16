package com.epam.spring.homework3.repository;

import com.epam.spring.homework3.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
