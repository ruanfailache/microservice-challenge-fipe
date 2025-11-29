package com.fipe.infrastructure.adapter.in.rest.dto.response;

import java.time.LocalDateTime;

/**
 * DTO for Error response
 */
public record ErrorResponse(
    String error,
    String message,
    LocalDateTime timestamp
) {
    public ErrorResponse(String error, String message) {
        this(error, message, LocalDateTime.now());
    }
}
