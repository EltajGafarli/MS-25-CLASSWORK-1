package org.example.ms25project.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ShoppingCartsResponseDto implements Serializable {
    private String name;

    @JsonAlias(value = "products")
    private List<ProductResponseDto> products;
}
