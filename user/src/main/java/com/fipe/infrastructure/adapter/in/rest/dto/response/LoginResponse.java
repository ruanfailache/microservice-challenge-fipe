package com.fipe.infrastructure.adapter.in.rest.dto.response;

public record LoginResponse (
     String token,
     String username,
     String role
) {}