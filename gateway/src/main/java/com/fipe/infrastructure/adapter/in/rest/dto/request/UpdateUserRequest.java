package com.fipe.infrastructure.adapter.in.rest.dto.request;

/**
 * DTO for updating a user
 */
public record UpdateUserRequest(
    String email,
    String role,
    Boolean active
) {}
