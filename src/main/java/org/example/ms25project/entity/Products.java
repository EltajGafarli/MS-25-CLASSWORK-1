package org.example.ms25project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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


    @Column(columnDefinition = "text")
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_details_id", referencedColumnName = "id")
    private ProductDetails productDetails;

    @OneToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.REFRESH,
            CascadeType.MERGE,
            CascadeType.PERSIST
    },
    mappedBy = "products")
    @JsonIgnore
    private Set<Category> categories = new HashSet<>();


    public void addCategory(Category category) {
        this.categories.add(category);
    }



    @Override
    public int hashCode() {
        return Objects
                .hash(
                        id, name, price, description, productDetails, categories
                );
    }


    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Products products)) return false;
        return Objects
                .equals(id, products.id)
                && Objects.equals(name, products.name)
                && Objects.equals(price, products.price)
                && Objects.equals(description, products.description)
                && Objects.equals(productDetails, products.productDetails)
                && Objects.equals(categories, products.categories);
    }
}
