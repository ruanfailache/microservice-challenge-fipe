package com.fipe.infrastructure.adapter.out.rest.request;

/**
 * Request DTO for user authentication
 */
public record UserAuthenticationRequest(
    String username,
    String password
) {}
