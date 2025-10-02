package com.project.shopapp.controllers;

import com.project.shopapp.services.product.ProductService;
import com.project.shopapp.services.product.image.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/product-images")
@RequiredArgsConstructor
public class ProductImageController {
    private final ProductImageService productImageService;
    private final ProductService productService;

    @DeleteMapping("/{productImageId}")
    public ResponseEntity<String> deleteProductImage(@PathVariable("productImageId") Long productImageId) {
        return ResponseEntity.ok("Product image deleted successfully. productImageId = " + productImageId);
    }

}
