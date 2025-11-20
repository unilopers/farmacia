package com.github.zambrinn.mvcproject.model.DTOs;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserTableResponse(
        UUID id,
        String name,
        String email,
        LocalDateTime createdAt
) {
}
