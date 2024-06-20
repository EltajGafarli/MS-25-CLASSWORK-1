package org.example.ms25project.dto;

import lombok.Builder;
import lombok.Data;
import org.example.ms25project.entity.enums.Category;

@Data
@Builder
public class ProductDto {
    private String name;
    private int price;
    private Category category;
    private String description;
}
