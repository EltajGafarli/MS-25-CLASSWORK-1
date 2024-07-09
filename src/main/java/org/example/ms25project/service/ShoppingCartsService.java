package org.example.ms25project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ms25project.dto.*;
import org.example.ms25project.entity.ProductDetails;
import org.example.ms25project.entity.Products;
import org.example.ms25project.entity.ShoppingCarts;
import org.example.ms25project.exception.NotFoundException;
import org.example.ms25project.repository.ProductsRepository;
import org.example.ms25project.repository.ShoppingCartsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartsService {
    private final ShoppingCartsRepository shoppingRepository;
    private final ProductsRepository productsRepository;


    public String createShoppingCart(ShoppingCartsDto shoppingCartsDto) {
        ShoppingCarts shoppingCart = ShoppingCarts.builder()
                .name(
                        shoppingCartsDto.getName()
                ).build();
        shoppingRepository.save(shoppingCart);
        return "Shopping created";
    }

    public String addProductToCart(long cartId, ProductRequest productRequest) {
        Products products = productsRepository.findById(productRequest.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        ShoppingCarts shoppingCart = shoppingRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException("Shopping cart not found"));

        shoppingCart.addProduct(products);

        this.shoppingRepository.save(shoppingCart);

        return "Product added to shopping cart";

    }


    public String removeProductFromCart(long cartId, long productId) {
        ShoppingCarts shoppingCart = this.shoppingRepository.findById(cartId).orElseThrow(() -> new NotFoundException("Shopping cart not found"));
        Products products = productsRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        shoppingCart.getProducts().removeIf(product -> product.getId() == products.getId());
        shoppingRepository.save(shoppingCart);
        return "Product removed from shopping cart";

    }

    @Transactional
    public ShoppingCartsResponseDto getShoppingCartById(long cartId) {
        ShoppingCarts shoppingCart = this.shoppingRepository.findById(cartId).orElseThrow(() -> new NotFoundException("Shopping cart not found"));
        Set<Products> products = shoppingCart.getProducts();
        List<ProductResponseDto> productResponses = new ArrayList<>();
        for (var prod : products) {
            ProductDetails productDetails = prod.getProductDetails();
            ProductResponseDto productResponse = ProductResponseDto
                    .builder()
                    .name(prod.getName())
                    .price(prod.getPrice())
                    .description(prod.getDescription())
                    .build();
            if (productDetails != null) {
                productResponse
                        .setProductDetails(
                                ProductDetailsDto
                                        .builder()
                                        .color(productDetails.getColor())
                                        .imageUrl(productDetails.getImageUrl())
                                        .build()
                        );
            }

            productResponses.add(productResponse);
        }

        return ShoppingCartsResponseDto
                .builder()
                .name(shoppingCart.getName())
                .products(productResponses)
                .build();
    }


}
