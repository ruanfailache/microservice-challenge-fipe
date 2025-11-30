package com.fipe.infrastructure.adapter.in.rest.dto.request;

public record UpdateUserRequest(
    String email,
    String role,
    Boolean active
) {}
