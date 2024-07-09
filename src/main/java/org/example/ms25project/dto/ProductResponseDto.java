package org.example.ms25project.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductResponseDto implements Serializable {
    private String name;
    private String description;
    private int price;

    @JsonAlias(value = "productDetails")
    private ProductDetailsDto productDetails;
}
