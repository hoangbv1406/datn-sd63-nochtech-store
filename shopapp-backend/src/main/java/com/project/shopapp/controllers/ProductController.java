package com.project.shopapp.controllers;

import com.project.shopapp.components.SecurityUtils;
import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.models.User;
import com.project.shopapp.responses.ResponseObject;
import com.project.shopapp.services.product.ProductService;
import com.project.shopapp.utils.FileUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final SecurityUtils securityUtils;

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
    public ResponseEntity<ResponseObject> createProduct(
            @Valid @RequestBody ProductDTO productDTO,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(
                    ResponseObject.builder()
                            .message(String.join("; ", errorMessages))
                            .status(HttpStatus.BAD_REQUEST)
                            .build()
            );
        }
        Product newProduct = productService.createProduct(productDTO);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Product created successfully.")
                        .status(HttpStatus.CREATED)
                        .data(newProduct)
                        .build()
        );
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ResponseObject> updateProduct(
            @PathVariable("productId") Long productId,
            @RequestBody ProductDTO productDTO
    ) {
        Product updatedProduct = productService.updateProduct(productId, productDTO);
        return ResponseEntity.ok(ResponseObject.builder()
                .data(updatedProduct)
                .message("Product updated successfully. productId = " + productId)
                .status(HttpStatus.OK)
                .build()
        );
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ResponseObject> deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(ResponseObject.builder()
                .data(null)
                .message(String.format("Product deleted successfully. productId = " + productId))
                .status(HttpStatus.OK)
                .build()
        );
    }

    @GetMapping("/by-ids")
    public ResponseEntity<String> getProductsByIds(@RequestParam("productsByIds") String productsByIds) {
        return ResponseEntity.ok("Products retrieved successfully. productsByIds = " + productsByIds);
    }

    @PostMapping(value = "uploads/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseObject> uploadImages(
            @PathVariable("productId") Long productId,
            @ModelAttribute("files") List<MultipartFile> files
    ) throws Exception {
        Product existingProduct = productService.getProductById(productId);
        files = files == null ? new ArrayList<MultipartFile>() : files;
        if (files.size() > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            return ResponseEntity.badRequest().body(
                    ResponseObject.builder()
                            .message("You can upload up to " + ProductImage.MAXIMUM_IMAGES_PER_PRODUCT + " images.")
                            .build()
            );
        }
        List<ProductImage> productImages = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.getSize() == 0) {
                continue;
            }
            if (file.getSize() > 10 * 1024 * 1024) {
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(ResponseObject.builder()
                        .message("File size must not exceed 10MB.")
                        .status(HttpStatus.PAYLOAD_TOO_LARGE)
                        .build()
                );
            }
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(ResponseObject.builder()
                        .message("File must be an image.")
                        .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .build()
                );
            }
            String filename = FileUtils.storeFile(file);
            ProductImage productImage = productService.createProductImage(existingProduct.getId(), ProductImageDTO.builder()
                    .imageUrl(filename)
                    .build()
            );
            productImages.add(productImage);
        }
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Images uploaded successfully. productId = " + productId)
                .status(HttpStatus.CREATED)
                .data(productImages)
                .build()
        );
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable("imageName") String imageName) {
        try {
            java.nio.file.Path imagePath = Paths.get("uploads/" + imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());
            if (resource.exists()) {
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
            } else {
                System.out.println("Image not found: uploads/" + imageName);
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(
                        new UrlResource(Paths.get("uploads/notfound.jpeg").toUri())
                );
            }
        } catch (Exception e) {
            System.err.println("Failed to retrieve image: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/like/{productId}")
    public ResponseEntity<ResponseObject> likeProduct(@PathVariable("productId") Long productId) throws Exception {
        User loginUser = securityUtils.getLoggedInUser();
        Product likedProduct = productService.likeProduct(loginUser.getId(), productId);
        return ResponseEntity.ok(ResponseObject.builder()
                .data(likedProduct)
                .message("Product liked successfully. productId = " + productId)
                .status(HttpStatus.OK)
                .build()
        );
    }

    @PostMapping("/unlike/{productId}")
    public ResponseEntity<ResponseObject> unlikeProduct(@PathVariable("productId") Long productId) throws Exception {
        User loginUser = securityUtils.getLoggedInUser();
        Product unlikedProduct = productService.unlikeProduct(loginUser.getId(), productId);
        return ResponseEntity.ok(ResponseObject.builder()
                .data(unlikedProduct)
                .message("Product unliked successfully. productId = " + productId)
                .status(HttpStatus.OK)
                .build()
        );
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
