package org.example.ms25project.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.ms25project.dto.ProductDetailsDto;
import org.example.ms25project.service.ProductDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/productDetails")
@RequiredArgsConstructor
public class ProductDetailsController {
    private final ProductDetailsService productDetailsService;

    @PostMapping(path = "/create")
    @Operation(
            summary = "Create Product details"
    )
    public ResponseEntity<String> create(@RequestBody ProductDetailsDto detailsDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        this.productDetailsService.createAProductDetails(detailsDto)
                );
    }


    @GetMapping(path = "/{id}")
    @Operation(
            summary = "Get Product Details"
    )
    public ResponseEntity<ProductDetailsDto> getProductDetails(@PathVariable long id) {
        return ResponseEntity
                .ok(
                        this.productDetailsService.getProductDetails(id)
                );
    }


    @GetMapping
    @Operation(
            summary = "Get All Product Details"
    )
    public ResponseEntity<List<ProductDetailsDto>> getAllProductDetails() {
        return ResponseEntity
                .ok(
                        this.productDetailsService.getAllProductDetails()
                );
    }

}
