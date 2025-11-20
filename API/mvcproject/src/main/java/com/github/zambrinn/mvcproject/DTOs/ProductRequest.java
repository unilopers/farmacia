package com.github.zambrinn.mvcproject.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(
        @NotBlank(message = "The product name most not be null")
        String productName,

        String description,

        @NotNull(message = "The sell price most not be null")
        double sellPrice,

        @NotBlank(message = "The category most not be null")
        String category,

        @NotNull(message = "The stock quantity most not be null")
        int stockQuantity
) {
}
