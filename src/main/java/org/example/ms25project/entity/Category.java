package org.example.ms25project.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @ManyToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH
    },
    fetch = FetchType.LAZY)
    @JoinColumn(name = "products_id", referencedColumnName = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Products products;


    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }


    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Category category)) {
            return false;
        }

        return Objects
                .equals(id, ((Category) obj).id)
                && Objects.equals(name, ((Category) obj).name);
    }
}
