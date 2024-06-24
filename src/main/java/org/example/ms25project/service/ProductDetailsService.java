package org.example.ms25project.service;

import lombok.RequiredArgsConstructor;
import org.example.ms25project.repository.ProductDetailsRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductDetailsService {
    private final ProductDetailsRepository productDetailsRepository;
}
