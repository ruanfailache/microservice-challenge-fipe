package com.fipe.infrastructure.adapter.out.rest.dto.response.user;

import java.time.LocalDateTime;

public record UserOutResponse (
        Long id,
        String username,
        String email,
        String role,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime lastLoginAt
) {}
