package com.fipe.infrastructure.adapter.in.rest.dto.request;

/**
 * DTO for changing password
 */
public record ChangePasswordRequest(
    String currentPassword,
    String newPassword
) {}
