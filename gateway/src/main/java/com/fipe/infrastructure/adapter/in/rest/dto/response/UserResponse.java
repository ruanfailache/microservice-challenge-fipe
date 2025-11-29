package com.fipe.infrastructure.adapter.in.rest.dto.response;

import com.fipe.domain.model.User;

import java.time.LocalDateTime;

/**
 * DTO for User response
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
) {
    public static UserResponse fromDomain(User user) {
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole() != null ? user.getRole().getName() : null,
            user.isActive(),
            user.getCreatedAt(),
            user.getUpdatedAt(),
            user.getLastLoginAt()
        );
    }
}
