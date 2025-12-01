package com.fipe.infrastructure.adapter.in.rest.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorInResponse {
    private String message;
    private LocalDateTime timestamp;

    public ErrorInResponse(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
