package org.example.ms25project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ms25project.dto.ProductDetailsDto;
import org.example.ms25project.entity.ProductDetails;
import org.example.ms25project.exception.NotFoundException;
import org.example.ms25project.repository.ProductDetailsRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class ProductDetailsService {
    private final ProductDetailsRepository productDetailsRepository;

    public String createAProductDetails(ProductDetailsDto dto) {
        ProductDetails productDetails = ProductDetails
                .builder()
                .color(dto.getColor())
                .imageUrl(dto.getImageUrl())
                .build();
        productDetailsRepository.save(productDetails);

        return "Product details created";
    }


    public List<ProductDetailsDto> getAllProductDetails() {
        return productDetailsRepository.findAll()
                .stream()
                .map(this::productToDto)
                .toList();
    }


    public ProductDetailsDto getProductDetails(long detailsId) {
        return this.productToDto(
                this.productDetailsRepository.findById(detailsId)
                        .orElseThrow(() -> new NotFoundException("Product Details Not Found"))
        );
    }


    private ProductDetailsDto productToDto(ProductDetails productDetails) {
        return ProductDetailsDto
                .builder()
                .color(productDetails.getColor())
                .imageUrl(productDetails.getImageUrl())
                .build();
    }
}
