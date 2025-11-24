package com.github.zambrinn.mvcproject.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.zambrinn.mvcproject.DTOs.SaleItemRequest;
import com.github.zambrinn.mvcproject.DTOs.SaleItemResponse;
import com.github.zambrinn.mvcproject.model.Product;
import com.github.zambrinn.mvcproject.model.SaleItem;
import com.github.zambrinn.mvcproject.repository.ProductRepository;
import com.github.zambrinn.mvcproject.repository.SaleItemRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class SaleItemService {

    @Autowired
    private SaleItemRepository saleItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private com.github.zambrinn.mvcproject.repository.SaleRepository saleRepository;

    @Transactional
    public SaleItemResponse createSaleItem(UUID saleId, SaleItemRequest request) {
        com.github.zambrinn.mvcproject.model.Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new EntityNotFoundException("Sale not found with ID: " + saleId));

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + request.productId()));

        if (product.getStockquantity() < request.quantity()) {
            throw new IllegalArgumentException("Insufficient stock for product: " + product.getProductName());
        }

        product.setStockquantity(product.getStockquantity() - request.quantity());
        productRepository.save(product);

        BigDecimal unitPrice = product.getSellPrice();
        
        SaleItem saleItem = SaleItem.builder()
                .sale(sale)
                .product(product)
                .quantity(request.quantity())
                .unitPrice(unitPrice)
                .build();

        saleItem = saleItemRepository.save(saleItem);

        return SaleItemResponse.fromEntity(saleItem);
    }

    @Transactional
    public List<SaleItemResponse> getSaleItems(UUID saleId) {
        return saleItemRepository.findBySaleId(saleId).stream()
                .map(SaleItemResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public SaleItemResponse updateSaleItem(UUID saleId, UUID itemId, SaleItemRequest request) {
        SaleItem saleItem = saleItemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("SaleItem not found with ID: " + itemId));

        if (!saleItem.getSale().getId().equals(saleId)) {
            throw new IllegalArgumentException("Item does not belong to the specified sale");
        }

        int oldQuantity = saleItem.getQuantity();
        int newQuantity = request.quantity();
        int diff = newQuantity - oldQuantity;

        if (diff != 0) {
            Product product = saleItem.getProduct();
            if (diff > 0 && product.getStockquantity() < diff) {
                throw new IllegalArgumentException("Insufficient stock to increase quantity");
            }
            product.setStockquantity(product.getStockquantity() - diff);
            productRepository.save(product);
        }

        saleItem.setQuantity(newQuantity);
        saleItem = saleItemRepository.save(saleItem);

        return SaleItemResponse.fromEntity(saleItem);
    }

    @Transactional
    public void removeItemFromSale(UUID saleId, UUID itemId) {
        SaleItem saleItem = saleItemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("SaleItem not found with ID: " + itemId));

        if (!saleItem.getSale().getId().equals(saleId)) {
            throw new IllegalArgumentException("Item does not belong to the specified sale");
        }

        Product product = saleItem.getProduct();
        product.setStockquantity(product.getStockquantity() + saleItem.getQuantity());
        productRepository.save(product);

        saleItemRepository.delete(saleItem);
    }
}