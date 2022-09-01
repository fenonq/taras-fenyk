package com.epam.spring.homework3.service.impl;

import com.epam.spring.homework3.dto.CategoryDto;
import com.epam.spring.homework3.exception.EntityNotFoundException;
import com.epam.spring.homework3.model.Category;
import com.epam.spring.homework3.repository.CategoryRepository;
import com.epam.spring.homework3.test.util.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    void findAllTest() {
        List<Category> categories = List.of(
                TestDataUtil.createCategory(),
                TestDataUtil.createCategory()
        );
        when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryDto> returnedCategories = categoryService.findAll();

        verify(categoryRepository).findAll();
        assertThat(returnedCategories, hasSize(categories.size()));
    }

    @Test
    void findByIdTest() {
        Category category = TestDataUtil.createCategory();
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        CategoryDto returnedCategory = categoryService.findById(anyLong());

        verify(categoryRepository).findById(anyLong());
        assertThat(returnedCategory, allOf(
                hasProperty("id", equalTo(category.getId())),
                hasProperty("name", equalTo(category.getName()))
        ));
    }

    @Test
    void findByIdNotFoundTest() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> categoryService.findById(anyLong()));
    }

    @Test
    void saveTest() {
        Category category = TestDataUtil.createCategory();
        CategoryDto categoryDto = TestDataUtil.createCategoryDto();
        when(categoryRepository.save(any())).thenReturn(category);

        CategoryDto returnedCategory = categoryService.save(categoryDto);

        assertThat(returnedCategory, allOf(
                hasProperty("id", equalTo(categoryDto.getId())),
                hasProperty("name", equalTo(categoryDto.getName()))
        ));
    }

    @Test
    void updateTest() {
        Category category = TestDataUtil.createCategory();
        CategoryDto categoryDto = TestDataUtil.createCategoryDto();
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryRepository.save(any())).thenReturn(category);

        CategoryDto returnedCategory = categoryService.update(category.getId(), categoryDto);

        assertThat(returnedCategory, allOf(
                hasProperty("id", equalTo(categoryDto.getId())),
                hasProperty("name", equalTo(categoryDto.getName()))
        ));
    }

    @Test
    void updateNotFoundTest() {
        CategoryDto categoryDto = TestDataUtil.createCategoryDto();
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
                () -> categoryService.update(categoryDto.getId(), categoryDto));
    }

    @Test
    void deleteByIdTest() {
        Category category = TestDataUtil.createCategory();
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        categoryService.deleteById(anyLong());

        verify(categoryRepository).delete(category);
    }

    @Test
    void deleteByIdNotFoundTest() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> categoryService.deleteById(anyLong()));
    }

    @Test
    void changeVisibilityTest() {
        Category category = TestDataUtil.createCategory();
        category.getDishes().add(TestDataUtil.createDish());
        boolean currentVisibility = category.isVisible();
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        CategoryDto returnedCategory;
        for (int i = 0; i < 3; i++) {
            returnedCategory = categoryService.changeVisibility(category.getId());
            currentVisibility = !currentVisibility;
            assertThat(returnedCategory, hasProperty("visible", is(currentVisibility)));
        }
    }

    @Test
    void changeVisibilityNotFoundTest() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> categoryService.changeVisibility(anyLong()));
    }

}
