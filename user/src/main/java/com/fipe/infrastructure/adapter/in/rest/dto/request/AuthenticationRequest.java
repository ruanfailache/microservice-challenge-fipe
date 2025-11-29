package com.fipe.infrastructure.adapter.in.rest.dto.request;

/**
 * DTO for authentication request
 */
public record AuthenticationRequest(
    String username,
    String password
) {}
