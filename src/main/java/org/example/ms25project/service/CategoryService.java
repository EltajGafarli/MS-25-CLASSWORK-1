package org.example.ms25project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ms25project.dto.CategoryDto;
import org.example.ms25project.entity.Category;
import org.example.ms25project.exception.AlreadyExistException;
import org.example.ms25project.exception.NotFoundException;
import org.example.ms25project.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public String createCategory(CategoryDto dto) {
        categoryRepository.findByName(dto.getName())
                .ifPresent((category) -> {throw new AlreadyExistException("Category already exist");});

        Category category = Category.builder()
                .name(dto.getName())
                .build();
        categoryRepository.save(category);
        return "Category created";
    }

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::categoryToDto)
                .toList();
    }


    public CategoryDto getCategory(long id) {
        return this.categoryToDto(
                this.categoryRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Category not found"))
        );
    }




    private CategoryDto categoryToDto(Category category) {
        return CategoryDto
                .builder()
                .name(category.getName())
                .build();
    }

}
