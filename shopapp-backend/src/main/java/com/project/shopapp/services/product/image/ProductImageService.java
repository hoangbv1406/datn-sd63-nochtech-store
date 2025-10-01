package com.project.shopapp.services.product.image;

import com.project.shopapp.repositories.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductImageService implements ProductImageServiceImpl {
    private final ProductImageRepository productImageRepository;
}
