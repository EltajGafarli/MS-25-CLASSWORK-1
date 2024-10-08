package org.example.ms25project.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.ms25project.dto.ProductDto;
import org.example.ms25project.entity.Category;
import org.example.ms25project.service.ProductsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(path = "/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductsService productsService;

    @PostMapping(path = "/create")
    @Operation(
            summary = "Create Product"
    )
    public ResponseEntity<String> createProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        productsService.createProduct(productDto)
                );
    }

    @GetMapping
    @Operation(
            summary = "Get all Products"
    )
    public ResponseEntity<List<ProductDto>> findAllProducts(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end, @RequestParam(required = false, defaultValue = "category") String sortBy, @RequestParam(defaultValue = "ASC") String direction) {
        return ResponseEntity.ok(productsService.findAllProducts(start, end, sortBy, direction));
    }

    @GetMapping(path = "/category")
    @Operation(
            summary = "Find Product counts"
    )
    public ResponseEntity<List<Map<Category, Integer>>> findAllCatogoryNames() {
        return ResponseEntity.ok(productsService.findCountByCategory());
    }


    @GetMapping("/{prodId}/category/{categoryId}")
    @Operation(
            summary = "add Category"
    )
    public ResponseEntity<ProductDto> addCategory(@PathVariable long prodId, @PathVariable long categoryId) {
        return ResponseEntity
                .ok(
                        this.productsService.addCategory(prodId, categoryId)
                );
    }


    @GetMapping("/{prodId}/details/{detailsId}")
    @Operation(
            summary = "Add Details"
    )
    public ResponseEntity<ProductDto> addDetails(@PathVariable long prodId, @PathVariable long detailsId) {
        return ResponseEntity
                .ok(
                        this.productsService.addProductDetails(prodId, detailsId)
                );
    }


    @DeleteMapping(path = "/{id}")
    @Operation(
            summary = "Delete Product"
    )
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        return ResponseEntity
                .ok(
                        this.productsService.deleteProduct(id)
                );
    }

}
