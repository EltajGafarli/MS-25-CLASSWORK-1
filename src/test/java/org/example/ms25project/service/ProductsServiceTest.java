package org.example.ms25project.service;

import org.example.ms25project.dto.ProductDetailsDto;
import org.example.ms25project.dto.ProductDto;
import org.example.ms25project.entity.Category;
import org.example.ms25project.entity.ProductDetails;
import org.example.ms25project.entity.Products;
import org.example.ms25project.mapper.ProductMapper;
import org.example.ms25project.repository.CategoryRepository;
import org.example.ms25project.repository.ProductDetailsRepository;
import org.example.ms25project.repository.ProductsRepository;
import org.example.ms25project.repository.proj.ProductProjection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductsServiceTest {
    @InjectMocks
    private ProductsService productsService;

    @Mock
    private ProductMapper productMapper;
    @Mock
    private ProductsRepository productsRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ProductDetailsRepository productDetailsRepository;

    private ProductDto productDto;
    private Products products;
    private Category category;
    private ProductDetails productDetails;

    @BeforeEach
    void setUp() {
        productDto = ProductDto
                .builder()
                .name("Test Product")
                .description("Test Description")
                .price(15)
                .build();

        products = Products
                .builder()
                .id(1L)
                .name("Test Product")
                .description("Test Description")
                .productDetails(null)
                .category(null)
                .price(15)
                .shoppingCarts(null)
                .build();

        category = Category
                .builder()
                .id(1L)
                .name("Test Category")
                .products(new HashSet<>())
                .build();

        productDetails = ProductDetails
                .builder()
                .id(1L)
                .color("Black")
                .imageUrl("imageUrl")
                .build();
    }


    @Test
    public void testFindAllProducts() {

        when(productsRepository.findAllByPriceRange(1, 20, Sort.by(
                Sort.Direction.fromString("ASC"),
                "test"
        ))).thenReturn(List.of(products, products));

        List<ProductDto> allProducts = productsService.findAllProducts(1, 20, "test", "ASC");

        Assertions.assertEquals(2, allProducts.size());
        Assertions.assertNotNull(allProducts);


    }


    @Test
    public void testDeleteProduct() {
        String response = productsService.deleteProduct(1L);
        Assertions.assertEquals("Product deleted successfully", response);
        verify(productsRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindCountByCategory() {

        ProductProjection projection = new ProductProjection() {
            @Override
            public Category getCategory() {
                return Category
                        .builder()
                        .id(1L)
                        .name("Test Category")
                        .build();
            }

            @Override
            public Integer getCnt() {
                return 2;
            }
        };

        when(productsRepository.findProductCount())
                .thenReturn(List.of(projection));

        List<Map<Category, Integer>> countByCategory = productsService.findCountByCategory();
        Assertions.assertEquals(1, countByCategory.size());
        verify(productsRepository, times(1)).findProductCount();
    }


    @Test
    public void testCreateProduct() {
        when(productMapper.dtoToProduct(productDto)).thenReturn(products);
        when(productsRepository.save(products)).thenReturn(products);
        String response = productsService.createProduct(productDto);

        Assertions.assertEquals("Product created successfully", response);
        verify(productsRepository, times(1)).save(products);
        verify(productMapper, times(1)).dtoToProduct(productDto);
    }

    @Test
    public void testAddCategory() {
        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));

        when(productsRepository.findById(1L))
                .thenReturn(Optional.of(products));

        when(productsRepository.save(products))
                .thenReturn(products);

        when(productMapper.productToDto(products))
                .thenReturn(productDto);

        ProductDto response = productsService.addCategory(1L, 1L);

        Assertions.assertEquals(productDto.getName(), response.getName());
        Assertions.assertEquals(productDto.getDescription(), response.getDescription());
        Assertions.assertEquals(productDto.getPrice(), response.getPrice());

        verify(productsRepository, times(1)).save(products);
        verify(productMapper, times(1)).productToDto(products);
        verify(productsRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddProductDetails() {
        when(productDetailsRepository.findById(1L))
                .thenReturn(Optional.of(productDetails));

        when(productsRepository.findById(1L))
                .thenReturn(Optional.of(products));

        when(productsRepository.save(products))
                .thenReturn(products);

        when(productMapper.productToDto(products))
                .thenReturn(productDto);

        ProductDto response = productsService.addProductDetails(1L, 1L);

        Assertions.assertEquals(productDto.getName(), response.getName());
        Assertions.assertEquals(productDto.getDescription(), response.getDescription());
        Assertions.assertEquals(productDto.getPrice(), response.getPrice());

        verify(productsRepository, times(1)).save(products);
        verify(productMapper, times(1)).productToDto(products);
        verify(productsRepository, times(1)).findById(1L);
        verify(productDetailsRepository, times(1)).findById(1L);

    }
}