package com.fipe.infrastructure.adapter.in.rest.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorInResponse {
    
    private String message;
    private LocalDateTime timestamp;
    private String details;
    
    public ErrorInResponse(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
    
    public ErrorInResponse(String message, String details) {
        this.message = message;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }
}
