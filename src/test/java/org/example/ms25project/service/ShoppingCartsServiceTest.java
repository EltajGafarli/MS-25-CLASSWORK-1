package org.example.ms25project.service;

import org.example.ms25project.dto.ProductRequest;
import org.example.ms25project.dto.ShoppingCartsDto;
import org.example.ms25project.dto.ShoppingCartsResponseDto;
import org.example.ms25project.entity.Products;
import org.example.ms25project.entity.ShoppingCarts;
import org.example.ms25project.exception.NotFoundException;
import org.example.ms25project.repository.ProductsRepository;
import org.example.ms25project.repository.ShoppingCartsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ShoppingCartsServiceTest {

    @InjectMocks
    private ShoppingCartsService shoppingCartsService;

    @Mock
    private ShoppingCartsRepository shoppingCartsRepository;

    @Mock
    private ProductsRepository productsRepository;

    @Mock
    private RedisTemplate<String, ShoppingCartsResponseDto> shoppingCartsRedisTemplate;

    @Mock
    private ValueOperations<String, ShoppingCartsResponseDto> valueOperations;


    private ShoppingCarts shoppingCarts;
    private ShoppingCartsDto shoppingCartsDto;
    private ShoppingCartsResponseDto shoppingCartsResponseDto;

    @BeforeEach
    void setUp() {
        shoppingCarts = ShoppingCarts
                .builder()
                .name("Test ShoppingCart")
                .id(1L)
                .products(new HashSet<>())
                .build();

        shoppingCartsDto = ShoppingCartsDto
                .builder()
                .name("Test ShoppingCart")
                .build();

        shoppingCartsResponseDto = ShoppingCartsResponseDto
                .builder()
                .name("Test ShoppingCart")
                .products(new ArrayList<>())
                .build();
    }

    @Test
    public void testCreateShoppingCart() {
        when(shoppingCartsRepository.save(any(ShoppingCarts.class))).thenReturn(shoppingCarts);
        when(shoppingCartsRedisTemplate.opsForValue()).thenReturn(valueOperations);

        String response = shoppingCartsService.createShoppingCart(shoppingCartsDto);

        Assertions.assertEquals("Shopping created", response);
        verify(shoppingCartsRepository, times(1)).save(shoppingCarts);
    }


    @Test
    void testAddProductToCart() {
        long cartId = 1L;
        ProductRequest productRequest = new ProductRequest(1L);
        ShoppingCarts shoppingCart = ShoppingCarts.builder().id(cartId).name("CartName").build();
        Products product = Products.builder().id(1L).name("ProductName").build();

        when(productsRepository.findById(1L)).thenReturn(Optional.of(product));
        when(shoppingCartsRepository.findById(cartId)).thenReturn(Optional.of(shoppingCart));
        when(shoppingCartsRedisTemplate.opsForValue()).thenReturn(valueOperations);

        String result = shoppingCartsService.addProductToCart(cartId, productRequest);

        assertEquals("Product added to shopping cart", result);
        verify(shoppingCartsRepository, times(1)).save(shoppingCart);
        verify(shoppingCartsRedisTemplate.opsForValue(), times(1)).getAndDelete(Long.toString(cartId));
    }

    @Test
    void testRemoveProductFromCart() {
        long cartId = 1L;
        long productId = 1L;
        Products product = Products.builder().id(productId).name("ProductName").build();
        ShoppingCarts shoppingCart = ShoppingCarts.builder().id(cartId).name("CartName").build();
        shoppingCart.addProduct(product);

        when(productsRepository.findById(productId)).thenReturn(Optional.of(product));
        when(shoppingCartsRepository.findById(cartId)).thenReturn(Optional.of(shoppingCart));
        when(shoppingCartsRedisTemplate.opsForValue()).thenReturn(valueOperations);

        String result = shoppingCartsService.removeProductFromCart(cartId, productId);

        assertEquals("Product removed from shopping cart", result);
        verify(shoppingCartsRepository, times(1)).save(shoppingCart);
        verify(shoppingCartsRedisTemplate.opsForValue(), times(1)).getAndDelete(Long.toString(cartId));
    }

    @Test
    void testGetShoppingCartById_CacheHit() {
        long cartId = 1L;
        ShoppingCartsResponseDto cachedResponse = ShoppingCartsResponseDto.builder().name("CartName").build();
        when(shoppingCartsRedisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(Long.toString(cartId))).thenReturn(cachedResponse);

        ShoppingCartsResponseDto result = shoppingCartsService.getShoppingCartById(cartId);

        assertEquals(cachedResponse, result);
        verify(shoppingCartsRedisTemplate.opsForValue(), times(1)).get(Long.toString(cartId));
        verify(shoppingCartsRepository, never()).findById(cartId);
    }

    @Test
    void testGetShoppingCartById_CacheMiss() {
        long cartId = 1L;
        ShoppingCarts shoppingCart = ShoppingCarts.builder().id(cartId).name("CartName").build();
        when(shoppingCartsRedisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(Long.toString(cartId))).thenReturn(null); // Simulate cache miss
        when(shoppingCartsRepository.findById(cartId)).thenReturn(Optional.of(shoppingCart));

        ShoppingCartsResponseDto result = shoppingCartsService.getShoppingCartById(cartId);

        assertEquals("CartName", result.getName());
        verify(shoppingCartsRedisTemplate.opsForValue(), times(1)).get(Long.toString(cartId));
        verify(shoppingCartsRepository, times(1)).findById(cartId); // DB call when cache miss
        verify(shoppingCartsRedisTemplate.opsForValue(), times(1)).set(Long.toString(cartId), result);
    }

    @Test
    void testGetShoppingCartById_NotFound() {
        long cartId = 1L;
        when(shoppingCartsRedisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(Long.toString(cartId))).thenReturn(null); // Simulate cache miss
        when(shoppingCartsRepository.findById(cartId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> shoppingCartsService.getShoppingCartById(cartId));
        verify(shoppingCartsRedisTemplate.opsForValue(), times(1)).get(Long.toString(cartId));
        verify(shoppingCartsRepository, times(1)).findById(cartId);
    }




}