package com.project.shopapp.controllers;

import com.project.shopapp.models.Product;
import com.project.shopapp.responses.ResponseObject;
import com.project.shopapp.services.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllProducts() {
        List<Product> product = productService.getAllProducts();
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Products retrieved successfully.")
                .status(HttpStatus.OK)
                .data(product)
                .build()
        );
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ResponseObject> getProductById(@PathVariable("productId") Long productId) {
        Product product = productService.getProductById(productId);
        return ResponseEntity.ok(ResponseObject.builder()
                .data(product)
                .message("Product retrieved successfully. productId = " + productId)
                .status(HttpStatus.OK)
                .build()
        );
    }

    @PostMapping("")
    public ResponseEntity<String> createProduct() {
        return ResponseEntity.ok("Product created successfully.");
    }

    @PutMapping("/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok("Product updated successfully. productId = " + productId);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok("Product deleted successfully. productId = " + productId);
    }

    @GetMapping("/by-ids")
    public ResponseEntity<String> getProductsByIds(@RequestParam("productsByIds") String productsByIds) {
        return ResponseEntity.ok("Products retrieved successfully. productsByIds = " + productsByIds);
    }

    @PostMapping("uploads/{productId}")
    public ResponseEntity<String> uploadImages(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok("Images uploaded successfully. productId = " + productId);
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<String> viewImage(@PathVariable("imageName") String imageName) {
        return ResponseEntity.ok("Image retrieved successfully. imageName = " + imageName);
    }

    @PostMapping("/like/{productId}")
    public ResponseEntity<String> likeProduct(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok("Product liked successfully. productId = " + productId);
    }

    @PostMapping("/unlike/{productId}")
    public ResponseEntity<String> unlikeProduct(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok("Product unliked successfully. productId = " + productId);
    }

    @PostMapping("/favorite-products")
    public ResponseEntity<String> findFavoriteProductsByUserId() {
        return ResponseEntity.ok("Favorite products retrieved successfully.");
    }

    @PostMapping("/generateFakeProducts")
    public ResponseEntity<String> generateFakeProducts() {
        return ResponseEntity.ok("Fake products generated successfully.");
    }

    @PostMapping("/generateFakeLikes")
    public ResponseEntity<String> generateFakeLikes() {
        return ResponseEntity.ok("Fake likes generated successfully.");
    }

}
