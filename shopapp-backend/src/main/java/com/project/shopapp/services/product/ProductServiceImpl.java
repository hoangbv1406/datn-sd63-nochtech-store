package com.project.shopapp.services.product;

import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.exceptions.InvalidParamException;
import com.project.shopapp.models.Favorite;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.repositories.FavoriteRepository;
import com.project.shopapp.repositories.ProductImageRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found."));
    }

    @Override
    public Product createProduct(ProductDTO productDTO) {
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .description(productDTO.getDescription())
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product updateProduct(long id, ProductDTO productDTO) {
        Product existingProduct = getProductById(id);
        if (existingProduct != null) {
            if (productDTO.getName() != null && !productDTO.getName().isEmpty()) {
                existingProduct.setName(productDTO.getName());
            }
            if (productDTO.getPrice() >= 0) {
                existingProduct.setPrice(productDTO.getPrice());
            }
            if (productDTO.getDescription() != null && !productDTO.getDescription().isEmpty()) {
                existingProduct.setDescription(productDTO.getDescription());
            }
            if (productDTO.getThumbnail() != null && !productDTO.getThumbnail().isEmpty()) {
                existingProduct.setThumbnail(productDTO.getThumbnail());
            }
            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public Product deleteProduct(long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found."));
        productRepository.deleteById(productId);
        return product;
    }

    @Override
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception {
        Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found."));
        ProductImage newProductImage = ProductImage.builder()
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        int size = productImageRepository.findByProductId(productId).size();
        if (size >= ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new InvalidParamException("Maximum allowed images: " + ProductImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        if (existingProduct.getThumbnail() == null) {
            existingProduct.setThumbnail(newProductImage.getImageUrl());
        }
        productRepository.save(existingProduct);
        return productImageRepository.save(newProductImage);
    }

    @Override
    public Product likeProduct(Long userId, Long productId) throws Exception {
        if (!userRepository.existsById(userId) || !productRepository.existsById(productId)) {
            throw new DataNotFoundException("User or product not found");
        }
        if (favoriteRepository.existsByUserIdAndProductId(userId, productId)) {
        } else {
            Favorite favorite = Favorite.builder()
                    .product(productRepository.findById(productId).orElse(null))
                    .user(userRepository.findById(userId).orElse(null))
                    .build();
            favoriteRepository.save(favorite);
        }
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public Product unlikeProduct(Long userId, Long productId) throws Exception {
        if (!userRepository.existsById(userId) || !productRepository.existsById(productId)) {
            throw new DataNotFoundException("User or product not found");
        }
        if (favoriteRepository.existsByUserIdAndProductId(userId, productId)) {
            Favorite favorite = favoriteRepository.findByUserIdAndProductId(userId, productId);
            favoriteRepository.delete(favorite);
        }
        return productRepository.findById(productId).orElse(null);
    }

}
