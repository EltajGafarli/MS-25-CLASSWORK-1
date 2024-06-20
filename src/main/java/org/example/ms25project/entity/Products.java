package org.example.ms25project.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.ms25project.entity.enums.Category;

@Entity
@Table(name = "products", uniqueConstraints = {
        @UniqueConstraint(name = "PRODUCT_NAME_UNIQUE", columnNames = {"name"})
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int price;
    @Enumerated
    private Category category;

    @Column(columnDefinition = "text")
    private String description;
}
