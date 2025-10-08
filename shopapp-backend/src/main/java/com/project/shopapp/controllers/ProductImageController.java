package com.project.shopapp.controllers;

import com.project.shopapp.models.ProductImage;
import com.project.shopapp.responses.ResponseObject;
import com.project.shopapp.services.product.ProductService;
import com.project.shopapp.services.product.image.ProductImageService;
import com.project.shopapp.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/product-images")
@RequiredArgsConstructor
public class ProductImageController {
    private final ProductImageService productImageService;
    private final ProductService productService;

    @DeleteMapping("/{productImageId}")
    public ResponseEntity<ResponseObject> deleteProductImage(
            @PathVariable("productImageId") Long productImageId
    ) throws Exception {
        ProductImage productImage = productImageService.deleteProductImage(productImageId);
        if (productImage != null) {
            FileUtils.deleteFile(productImage.getImageUrl());
        }
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Product image deleted successfully. productImageId = " + productImageId)
                .data(productImage)
                .status(HttpStatus.OK)
                .build()
        );
    }

}
