package com.fipe.infrastructure.adapter.in.rest.dto.request;

public record LoginRequest (
    String username,
    String password
) {}