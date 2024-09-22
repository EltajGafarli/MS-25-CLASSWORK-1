package org.example.ms25project.service;

import org.example.ms25project.dto.CategoryDto;
import org.example.ms25project.entity.Category;
import org.example.ms25project.exception.NotFoundException;
import org.example.ms25project.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    private Category testCategory;
    private CategoryDto testCategoryDto;

    @BeforeEach
    void setUp() {
        testCategory = Category
                .builder()
                .id(1L)
                .name("Test Category")
                .products(Set.of())
                .build();

        testCategoryDto = CategoryDto
                .builder()
                .name("Test Category")
                .build();
    }

    @Test
    public void testGetCategoryByIdSuccess() {


        when(categoryRepository.findById(anyLong()))
                .thenReturn(
                        Optional.of(testCategory)
                );

        CategoryDto category = categoryService.getCategory(1L);

        Assertions.assertEquals(category.getName(), testCategory.getName());
        verify(categoryRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testGetCategoryByIdException() {
        when(categoryRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.getCategory(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Category not found");
    }

    @Test
    public void testGetAllCategoriesSuccess() {
        when(categoryRepository.findAll())
                .thenReturn(
                        List.of(testCategory)
                );

        List<CategoryDto> categories = categoryService.getAllCategories();

        Assertions.assertEquals(1, categories.size());
    }

    @Test
    public void testCreateCategorySuccess() {
        when(categoryRepository.save(any())).thenReturn(testCategory);

        String response = categoryService.createCategory(testCategoryDto);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("Category created", response);

    }

}