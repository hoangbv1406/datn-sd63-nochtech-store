package com.project.shopapp.services.product;

import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(long id);
    Product createProduct(ProductDTO productDTO);
    Product updateProduct(long id, ProductDTO productDTO);
    Product deleteProduct(long id);
    Product likeProduct(Long userId, Long productId) throws Exception;
    Product unlikeProduct(Long userId, Long productId) throws Exception;
    ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception;
}
