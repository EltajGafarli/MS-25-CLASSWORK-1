package org.example.ms25project.repository.proj;

import org.example.ms25project.entity.enums.Category;

public interface ProductProjection {
    Category getCategory();
    Integer getCnt();
}
