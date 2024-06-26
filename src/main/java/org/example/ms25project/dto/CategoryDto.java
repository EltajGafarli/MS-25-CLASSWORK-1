package org.example.ms25project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Data
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto implements Serializable {
    private String name;
}
