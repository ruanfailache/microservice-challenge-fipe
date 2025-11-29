package com.fipe.infrastructure.adapter.out.rest.response;

import java.time.LocalDateTime;

/**
 * Response DTO from user service
 */
public record UserServiceResponse(
    Long id,
    String username,
    String email,
    String role,
    boolean active,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime lastLoginAt
) {}
