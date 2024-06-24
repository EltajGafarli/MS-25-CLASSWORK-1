package org.example.ms25project.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDetailsDto {
    private String color;
    private String imageUrl;
}
