package org.example.ms25project.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.ms25project.dto.CategoryDto;
import org.example.ms25project.service.CategoryService;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping(path = "/create")
    @Operation(
            method = "POST",
            description = "Create Category",
            summary = "Create Category"
    )
    public ResponseEntity<String> createCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        categoryService.createCategory(categoryDto)
                );
    }


    @GetMapping(path = "/{id}")
    @Operation(
            summary = "Get Category By ID"
    )
    public ResponseEntity<CategoryDto> getCategory(@PathVariable long id) {
        return ResponseEntity
                .ok(
                        this.categoryService.getCategory(id)
                );
    }

    @GetMapping
    @Operation(summary = "Get all Categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity
                .ok(
                        categoryService.getAllCategories()
                );
    }
}
