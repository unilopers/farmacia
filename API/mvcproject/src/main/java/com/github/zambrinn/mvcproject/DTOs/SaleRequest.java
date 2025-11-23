package com.github.zambrinn.mvcproject.DTOs;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;
import java.util.UUID;

public record SaleRequest(

        @NotBlank(message = "the customer ID must not be null")
        UUID customerId,

        @NotEmpty (message = "the item list must not be null")
        @Valid
        List<SaleItemRequest> items






) {
    public record SaleItemRequest(
            @NotNull(message = "Product ID is required")
            UUID productId,

            @NotNull(message = "Quantity is required")
            @Positive(message = "Quantity must be positive")
            Integer quantity
    ) {}



}
