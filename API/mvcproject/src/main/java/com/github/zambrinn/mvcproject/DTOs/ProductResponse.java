package com.github.zambrinn.mvcproject.DTOs;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String productName,
        String description,
        String category,
        double sellPice,
        int stockQuantity,
        LocalDateTime createdAt
) {
}
