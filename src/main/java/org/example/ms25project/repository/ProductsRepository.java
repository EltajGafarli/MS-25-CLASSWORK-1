package org.example.ms25project.repository;

import org.example.ms25project.entity.Products;
import org.example.ms25project.repository.proj.ProductProjection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {

    @Query("SELECT p FROM Products p WHERE " +
            "(:startPrice IS NULL OR p.price >= :startPrice) AND " +
            "(:endPrice IS NULL OR p.price <= :endPrice)")
    List<Products> findAllByPriceRange(@Param("startPrice") Integer startPrice,
                                       @Param("endPrice") Integer endPrice,
                                       Sort sort);

    @Query(value = "select prod.category as category, count(prod) as cnt from Products prod group by prod.category")
    List<ProductProjection> findProductCount();

}
