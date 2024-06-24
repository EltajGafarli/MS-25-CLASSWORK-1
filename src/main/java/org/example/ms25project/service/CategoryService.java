package org.example.ms25project.service;

import lombok.RequiredArgsConstructor;
import org.example.ms25project.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
}
