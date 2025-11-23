package com.github.zambrinn.mvcproject.DTOs;

import jakarta.validation.constraints.NotBlank;

public record CustomerRequest(
        @NotBlank(message = "The customer must have a name")
        String name,
        @NotBlank(message = "The customer must have a CPF")
        String cpf,
        @NotBlank(message = "The customer must have a CPF")
        String email,
        @NotBlank(message = "The customer must have a password")
        String passwordHash,
        String phone
) {
}
