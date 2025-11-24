package com.github.zambrinn.mvcproject.DTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String productName,
        String description,
        String category,
        BigDecimal sellPrice,
        int stockQuantity,
        LocalDateTime createdAt
) {
}
