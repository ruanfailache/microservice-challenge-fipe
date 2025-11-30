package com.fipe.infrastructure.adapter.in.rest.dto.request;

public record AuthenticationRequest(
    String username,
    String password
) {}
