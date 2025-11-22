package com.github.zambrinn.mvcproject.DTOs;

import com.github.zambrinn.mvcproject.model.SaleItem;
import java.math.BigDecimal;
import java.util.UUID;

public record SaleItemResponse(
        UUID id,
        UUID productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal totalPrice
) {
    public static SaleItemResponse fromEntity(SaleItem saleItem) {
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