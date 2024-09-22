package org.example.ms25project.service;

import org.example.ms25project.dto.ProductDetailsDto;
import org.example.ms25project.entity.ProductDetails;
import org.example.ms25project.repository.ProductDetailsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductDetailsServiceTest {

    @InjectMocks
    private ProductDetailsService productDetailsService;
    @Mock
    private ProductDetailsRepository productDetailsRepository;

    private ProductDetails productDetails;
    private ProductDetailsDto productDetailsDto;


    @BeforeEach
    void setUp() {
        productDetails = ProductDetails
                .builder()
                .id(1L)
                .imageUrl("imageUrl")
                .color("color")
                .build();

        productDetailsDto = ProductDetailsDto
                .builder()
                .color("color")
                .imageUrl("imageUrl")
                .build();
    }

    @Test
    public void testCreateProductDetails() {
        when(productDetailsRepository.save(productDetails))
                .thenReturn(productDetails);

        String response = productDetailsService.createAProductDetails(productDetailsDto);
        Assertions.assertEquals("Product details created", response);
    }

    @Test
    public void testGetAllProductDetails() {
        when(productDetailsRepository.findAll())
                .thenReturn(
                        List.of(productDetails)
                );

        List<ProductDetailsDto> allProductDetails = productDetailsService.getAllProductDetails();
        Assertions.assertEquals(1, allProductDetails.size());

        verify(productDetailsRepository, times(1)).findAll();
    }

    @Test
    public void testGetProductDetailsById() {
        when(productDetailsRepository.findById(1L))
                .thenReturn(
                        Optional.of(productDetails)
                );

        ProductDetailsDto productDetailsDto = productDetailsService.getProductDetails(1L);
        Assertions.assertNotNull(productDetailsDto);
        verify(productDetailsRepository, times(1)).findById(1L);
    }
}