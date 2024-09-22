package org.example.ms25project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "products", uniqueConstraints = {
        @UniqueConstraint(name = "PRODUCT_NAME_UNIQUE", columnNames = {"name"})
})
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class Products implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int price;


    @Column(columnDefinition = "text")
    private String description;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_details_id", referencedColumnName = "id")
    @ToString.Exclude
    private ProductDetails productDetails;

    @ManyToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH
    },
            fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Category category;


    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY, mappedBy = "products")
    @ToString.Exclude
    private Set<ShoppingCarts> shoppingCarts = new HashSet<>();

    public void addShoppingCart(ShoppingCarts shoppingCart) {
        this.shoppingCarts.add(shoppingCart);
    }


    @Override
    public int hashCode() {
        return Objects
                .hash(
                        id, name, price, description, productDetails, category
                );
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Products products)) return false;
        return Objects
                .equals(id, products.id)
                && Objects.equals(name, products.name)
                && Objects.equals(price, products.price)
                && Objects.equals(description, products.description)
                && Objects.equals(productDetails, products.productDetails)
                && Objects.equals(category, products.category);
    }

}
