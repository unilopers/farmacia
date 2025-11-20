package com.github.zambrinn.mvcproject.service;

import com.github.zambrinn.mvcproject.DTOs.ProductRequest;
import com.github.zambrinn.mvcproject.DTOs.ProductResponse;
import com.github.zambrinn.mvcproject.model.Product;
import com.github.zambrinn.mvcproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest request){
        Product product = Product.builder()
                .productName(request.productName())
                .description(request.description())
                .category(request.category())
                .sellPrice(request.sellPrice())
                .stockquantity(request.stockQuantity())
                .createdAt(LocalDateTime.now())
                .build();

        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    public List<ProductResponse> getAllProducts(){
        return productRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductResponse updateProduct(UUID id, ProductRequest request){
        Product foundProduct = productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Product not found with id " + id)
        );
        foundProduct.setProductName(request.productName());
        foundProduct.setDescription(request.description());
        foundProduct.setCategory(request.category());
        foundProduct.setSellPrice(request.sellPrice());
        foundProduct.setStockquantity(request.stockQuantity());
        foundProduct.setCreatedAt(LocalDateTime.now());
        Product updatedProduct = productRepository.save(foundProduct);
        return convertToDTO(updatedProduct);
    }

    public void deleteProduct(UUID id){
        Product foundProduct = productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Product not found with id " + id)
        );
        productRepository.delete(foundProduct);
    }


    public ProductResponse convertToDTO(Product product){
        return new ProductResponse(
                product.getId(),
                product.getProductName(),
                product.getDescription(),
                product.getCategory(),
                product.getSellPrice(),
                product.getStockquantity(),
                product.getCreatedAt()
        );
    }
}
