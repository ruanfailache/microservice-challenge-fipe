package com.fipe.domain.exception;

public class VehicleDataProcessingException extends RuntimeException {
    
    public VehicleDataProcessingException(String message) {
        super(message);
    }
    
    public VehicleDataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
