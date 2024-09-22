package org.example.ms25project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ms25project.dto.ProductDto;
import org.example.ms25project.entity.Category;
import org.example.ms25project.entity.ProductDetails;
import org.example.ms25project.entity.Products;
import org.example.ms25project.exception.NotFoundException;
import org.example.ms25project.mapper.ProductMapper;
import org.example.ms25project.repository.CategoryRepository;
import org.example.ms25project.repository.ProductDetailsRepository;
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
    private final CategoryRepository categoryRepository;
    private final ProductDetailsRepository productDetailsRepository;

    @Transactional(rollbackOn = Exception.class)
    public String createProduct(ProductDto productDto) {
        Products products = productMapper.dtoToProduct(productDto);
        productsRepository.save(products);
        return "Product created successfully";
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
        for (var proj : productCount) {
            result.add(Map.of(proj.getCategory(), proj.getCnt()));
        }

        return result;
    }


    public String deleteProduct(long id) {
        this.productsRepository.deleteById(id);
        return "Product deleted successfully";
    }


    public ProductDto addCategory(long prodId, long categoryId) {
        var category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category Not found"));
        Products product = this.productsRepository.findById(prodId).orElseThrow(() -> new NotFoundException("Product not found"));
        category.addProduct(product);

        this.productsRepository.save(product);

        return this.productMapper.productToDto(product);
    }


    public ProductDto addProductDetails(long prodId, long detailsId) {
        ProductDetails productDetails = this.productDetailsRepository.findById(detailsId)
                .orElseThrow(() -> new NotFoundException("Product Details not found"));

        Products product = this.productsRepository.findById(prodId)
                .orElseThrow(
                        () -> new NotFoundException("Product not found")
                );

        product.setProductDetails(productDetails);
        Products save = this.productsRepository.save(product);
        return this.productMapper.productToDto(save);
    }
}
