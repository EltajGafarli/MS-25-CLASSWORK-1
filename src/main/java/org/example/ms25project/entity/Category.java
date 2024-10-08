package org.example.ms25project.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "categories", uniqueConstraints = {
        @UniqueConstraint(
                name = "UNIQUE_CATEGORY",
                columnNames = {"name"}
        )
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "category_name")
    private String name;

    @OneToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.REFRESH,
            CascadeType.MERGE,
            CascadeType.PERSIST
    },
            mappedBy = "category")
    @JsonIgnore
    private Set<Products> products = new HashSet<>();


    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Category category)) {
            return false;
        }

        return Objects
                .equals(id, category.id)
                && Objects.equals(name, category.name);
    }


    public void addProduct(Products products) {
        this.products.add(products);
        products.setCategory(this);
    }
}
