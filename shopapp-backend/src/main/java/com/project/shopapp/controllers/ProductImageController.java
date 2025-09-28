package com.project.shopapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/product-images")
public class ProductImageController {

    @DeleteMapping("/{productImageId}")
    public ResponseEntity<String> deleteProductImage(@PathVariable("productImageId") Long productImageId) {
        return ResponseEntity.ok("Product image deleted successfully. productImageId = " + productImageId);
    }

}
