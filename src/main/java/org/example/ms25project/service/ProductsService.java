package org.example.ms25project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ms25project.dto.ProductDto;
import org.example.ms25project.entity.Products;
import org.example.ms25project.entity.enums.Category;
import org.example.ms25project.mapper.ProductMapper;
import org.example.ms25project.repository.ProductsRepository;
import org.example.ms25project.repository.proj.ProductProjection;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final ProductMapper productMapper;

    @Transactional(rollbackOn = Exception.class)
    public String createProduct(ProductDto productDto) {
        Products products = productMapper.dtoToProduct(productDto);
        productsRepository.save(products);
        return "Product Created successfully";
    }


    public List<ProductDto> findAllProducts(Integer start, Integer end, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        return productsRepository.findAllByPriceRange(start, end, sort)
                .stream()
                .map(productMapper::productToDto)
                .toList();
    }

    public List<Map<Category, Integer>> findCountByCategory() {
        List<ProductProjection> productCount = productsRepository.findProductCount();
        List<Map<Category, Integer>> result = new ArrayList<>();
        for(var proj: productCount) {
            result.add(Map.of(proj.getCategory(), proj.getCnt()));
        }
        return result;
    }







}
