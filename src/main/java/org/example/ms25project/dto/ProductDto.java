package org.example.ms25project.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
    private String name;
    private int price;
    private String description;
}
