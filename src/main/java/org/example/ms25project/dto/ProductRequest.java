package org.example.ms25project.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor

@Data
@Builder
public class ProductRequest implements Serializable {
    private long productId;
}
