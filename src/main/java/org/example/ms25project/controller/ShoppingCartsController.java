package org.example.ms25project.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.ms25project.dto.ProductRequest;
import org.example.ms25project.dto.ShoppingCartsDto;
import org.example.ms25project.dto.ShoppingCartsResponseDto;
import org.example.ms25project.service.ShoppingCartsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/shopping-carts")
@RequiredArgsConstructor
public class ShoppingCartsController {
    private final ShoppingCartsService shoppingCartsService;

    @PostMapping
    @Operation(
            summary = "Create Shopping Carts"
    )
    public ResponseEntity<String> createShoppingCart(@RequestBody ShoppingCartsDto shoppingCartsDto) {
        return ResponseEntity.status(
                HttpStatus.CREATED
        ).body(
                shoppingCartsService.createShoppingCart(shoppingCartsDto)
        );
    }


    @PostMapping(path = "/{id}/product")
    @Operation(
            summary = "Add Product To Cart"
    )
    public ResponseEntity<String> addProductToCart(@PathVariable long id, @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(
                this.shoppingCartsService.addProductToCart(id, productRequest)
        );
    }


    @DeleteMapping(path = "/{id}/product/{productId}")
    @Operation(
            summary = "Remove Product From Cart"
    )
    public ResponseEntity<String> removeProductFromCart(@PathVariable(name = "id") long cartId, @PathVariable long productId) {
        return ResponseEntity
                .ok(
                        this.shoppingCartsService.removeProductFromCart(cartId, productId)
                );
    }

    @GetMapping(path = "/{id}")
    @Operation(
            summary = "Get Shopping Cart By Id"
    )
    public ResponseEntity<ShoppingCartsResponseDto> getShoppingCartById(@PathVariable long id) {
        return ResponseEntity
                .ok(
                        this.shoppingCartsService.getShoppingCartById(id)
                );
    }

}
