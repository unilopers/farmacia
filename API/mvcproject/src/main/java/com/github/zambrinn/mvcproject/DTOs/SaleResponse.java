package com.github.zambrinn.mvcproject.DTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record SaleResponse(
        UUID saleId,
        UUID customerId,
        String customerName,
        UUID sellerId,
        String sellerName,
        List<SaleItemDTO> saleItems,
        LocalDateTime saleDate,
        BigDecimal totalAmount
) {
    public record SaleItemDTO(
            UUID id,
            UUID productId,
            String productName,
            Integer quantity,
            BigDecimal unitPrice
    ) {
    }
}