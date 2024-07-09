package org.example.ms25project.repository;

import org.example.ms25project.entity.ShoppingCarts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartsRepository extends JpaRepository<ShoppingCarts, Long> {
}
