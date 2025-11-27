package com.github.zambrinn.mvcproject.DTOs;

import com.github.zambrinn.mvcproject.model.enums.UserRole;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record UserTableRequest(
        @NotBlank(message = "The user name must not be null")
        String name,

        @NotBlank(message = "The password must not be null")
        String passwordHash,

        @NotBlank(message = "The user email must not be null")
        String email,

        @NotBlank(message = "The user cpf must not be null")
        String cpf,

        UserRole role,
        
        @Valid
        AddressRequest address  // Opcional
) {
}
