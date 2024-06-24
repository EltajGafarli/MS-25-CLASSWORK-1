package org.example.ms25project.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "product_details")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String color;
    private String imageUrl;

    @OneToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    },
    mappedBy = "productDetails")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Products products;


    @Override
    public int hashCode() {
        return Objects
                .hash(color, imageUrl, id);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ProductDetails productDetails)) {
            return false;
        }

        return Objects.equals(color, productDetails.color)
                && Objects.equals(imageUrl, productDetails.imageUrl)
                && Objects.equals(id, productDetails.id);
    }
}
