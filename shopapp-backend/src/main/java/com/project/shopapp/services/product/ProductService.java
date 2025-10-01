package com.project.shopapp.services.product;

import com.project.shopapp.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductServiceImpl {
    private final ProductRepository productRepository;
}
