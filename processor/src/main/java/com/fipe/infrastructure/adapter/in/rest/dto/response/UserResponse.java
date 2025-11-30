package com.fipe.infrastructure.adapter.in.rest.dto.response;

import java.time.LocalDateTime;

/**
 * DTO for User response from User Service
 */
public record UserResponse(
    Long id,
    String username,
    String email,
    String role,
    boolean active,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime lastLoginAt
) {}

