package com.github.zambrinn.mvcproject.DTOs;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CustomerResponse(
        UUID id,
        String name,
        String cpf,
        String email,
        String phone,
        LocalDateTime createdAt,
        List<AddressDTO> addresses  
) {
    public record AddressDTO (
           UUID id,
           String street,
           String state,
           String city,
           String number
    ) {
    }
}
