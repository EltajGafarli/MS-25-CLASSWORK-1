package org.example.ms25project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ms25project.dto.*;
import org.example.ms25project.entity.ProductDetails;
import org.example.ms25project.entity.Products;
import org.example.ms25project.entity.ShoppingCarts;
import org.example.ms25project.exception.NotFoundException;
import org.example.ms25project.repository.ProductsRepository;
import org.example.ms25project.repository.ShoppingCartsRepository;
import org.hibernate.Hibernate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ShoppingCartsService {
    private final ShoppingCartsRepository shoppingRepository;
    private final ProductsRepository productsRepository;
    private final RedisTemplate<String, ShoppingCartsResponseDto> shoppingCartsRedisTemplate;

    @Transactional
    public String createShoppingCart(ShoppingCartsDto shoppingCartsDto) {
        ShoppingCarts shoppingCart = ShoppingCarts.builder()
                .name(
                        shoppingCartsDto.getName()
                ).build();
        ShoppingCarts save = shoppingRepository.save(shoppingCart);
        shoppingCartsRedisTemplate.opsForValue().set(Long.toString(save.getId()), toDto(save));
        return "Shopping created";
    }

    @Transactional
    public String addProductToCart(long cartId, ProductRequest productRequest) {
        Products products = productsRepository.findById(productRequest.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        ShoppingCarts shoppingCart = shoppingRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException("Shopping cart not found"));

        shoppingCart.addProduct(products);

        this.shoppingRepository.save(shoppingCart);

        this.shoppingCartsRedisTemplate.delete(Long.toString(cartId));

        return "Product added to shopping cart";

    }


    @Transactional
    public String removeProductFromCart(long cartId, long productId) {
        ShoppingCarts shoppingCart = this.shoppingRepository.findById(cartId).orElseThrow(() -> new NotFoundException("Shopping cart not found"));
        Products products = productsRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        shoppingCart.getProducts().removeIf(product -> product.getId() == products.getId());
        shoppingRepository.save(shoppingCart);
        this.shoppingCartsRedisTemplate.delete(Long.toString(cartId));
        return "Product removed from shopping cart";

    }

    @Transactional
    public ShoppingCartsResponseDto getShoppingCartById(long cartId) {
        ShoppingCartsResponseDto shoppingCartsResponse;
        if(this.shoppingCartsRedisTemplate.opsForValue().get(Long.toString(cartId)) != null) {
            shoppingCartsResponse = this.shoppingCartsRedisTemplate.opsForValue().get(Long.toString(cartId));
            log.info("{}", "Getting from Redis");
        } else {
            ShoppingCarts shoppingCart = this.shoppingRepository.findById(cartId).orElseThrow(() -> new NotFoundException("Shopping cart not found"));
            Hibernate.initialize(shoppingCart.getProducts());
            shoppingCartsResponse = toDto(shoppingCart);
            shoppingCartsRedisTemplate.opsForValue().set(Long.toString(shoppingCart.getId()), shoppingCartsResponse);

        }

        return shoppingCartsResponse;
    }


    private ShoppingCartsResponseDto toDto(ShoppingCarts shoppingCart) {
        List<ProductResponseDto> productResponses = new ArrayList<>();
        for (Products prod : shoppingCart.getProducts()) {
            ProductDetails productDetails = prod.getProductDetails();
            ProductResponseDto productResponse = ProductResponseDto
                    .builder()
                    .name(prod.getName())
                    .price(prod.getPrice())
                    .description(prod.getDescription())
                    .build();

            if (productDetails != null) {
                productResponse.setProductDetails(
                        ProductDetailsDto.builder()
                                .color(productDetails.getColor())
                                .imageUrl(productDetails.getImageUrl())
                                .build()
                );
            }

            productResponses.add(productResponse);
        }

        return ShoppingCartsResponseDto.builder()
                .name(shoppingCart.getName())
                .products(productResponses)
                .build();
    }

}
