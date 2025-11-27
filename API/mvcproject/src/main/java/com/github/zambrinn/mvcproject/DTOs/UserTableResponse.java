package com.github.zambrinn.mvcproject.DTOs;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.github.zambrinn.mvcproject.model.enums.UserRole;

public record UserTableResponse(
        UUID id,
        String name,
        String email,
        LocalDateTime createdAt,
        UserRole role,
        List<AddressDTO> addresses
) {
    public record AddressDTO(
            UUID id,
            String street,
            String number,
            String city,
            String state
    ) {
    }
}
