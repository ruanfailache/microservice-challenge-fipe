package com.fipe.infrastructure.adapter.in.rest.dto.request;

public record ChangePasswordRequest(
    String currentPassword,
    String newPassword
) {}
