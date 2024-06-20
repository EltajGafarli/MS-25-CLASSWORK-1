package org.example.ms25project.mapper;

import org.example.ms25project.dto.ProductDto;
import org.example.ms25project.entity.Products;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Products dtoToProduct(ProductDto dto) {
        return Products
                .builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .category(dto.getCategory())
                .build();
    }

    public ProductDto productToDto(Products products) {
        return ProductDto
                .builder()
                .name(products.getName())
                .category(products.getCategory())
                .description(products.getDescription())
                .price(products.getPrice())
                .build();
    }
}
