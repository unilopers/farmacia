package com.github.zambrinn.mvcproject.DTOs;

import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerResponse(
        UUID id,
        String email,
        LocalDateTime createdAt
) {
}
