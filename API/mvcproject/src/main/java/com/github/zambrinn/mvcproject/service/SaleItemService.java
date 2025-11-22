package com.github.zambrinn.mvcproject.service;

import com.github.zambrinn.mvcproject.DTOs.SaleItemRequest;
import com.github.zambrinn.mvcproject.DTOs.SaleItemResponse;
import com.github.zambrinn.mvcproject.model.Product;
import com.github.zambrinn.mvcproject.model.SaleItem;
import com.github.zambrinn.mvcproject.repository.ProductRepository;
import com.github.zambrinn.mvcproject.repository.SaleItemRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class SaleItemService {

    @Autowired
    private SaleItemRepository saleItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public SaleItemResponse createSaleItem(UUID saleId, SaleItemRequest request) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + request.productId()));

        if (product.getStockquantity() < request.quantity()) {
            throw new IllegalArgumentException("Insufficient stock for product: " + product.getProductName());
        }

        product.setStockquantity(product.getStockquantity() - request.quantity());
        productRepository.save(product);

        BigDecimal unitPrice = BigDecimal.valueOf(product.getSellPrice());
        
        SaleItem saleItem = SaleItem.builder()
                .saleId(saleId)
                .product(product)
                .quantity(request.quantity())
                .unitPrice(unitPrice)
                .build();

        saleItem = saleItemRepository.save(saleItem);

        return mapToResponse(saleItem);
    }

    private SaleItemResponse mapToResponse(SaleItem saleItem) {
        BigDecimal totalPrice = saleItem.getUnitPrice().multiply(BigDecimal.valueOf(saleItem.getQuantity()));
        
        return new SaleItemResponse(
                saleItem.getId(),
                saleItem.getProduct().getId(),
                saleItem.getProduct().getProductName(),
                saleItem.getQuantity(),
                saleItem.getUnitPrice(),
                totalPrice
        );
    }
}
