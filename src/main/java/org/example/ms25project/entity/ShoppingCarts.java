package org.example.ms25project.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ShoppingCarts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;

    @ManyToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    },
            fetch = FetchType.LAZY)
    @JoinTable(name = "shoppingcart_product", joinColumns = @JoinColumn(name = "shopping_id"), inverseJoinColumns = @JoinColumn(name = "products_id"))
    private Set<Products> products = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects
                .hash(id, name);
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ShoppingCarts shoppingCarts)) return false;

        return Objects
                .equals(shoppingCarts.getId(), this.getId())
                && Objects.equals(shoppingCarts.getName(), this.getName());
    }


    public void addProduct(Products product) {
        products.add(product);
        product.addShoppingCart(this);

    }
}
