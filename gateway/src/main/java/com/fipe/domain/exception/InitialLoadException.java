package com.fipe.domain.exception;

public class InitialLoadException extends RuntimeException {
    
    public InitialLoadException(String message) {
        super(message);
    }
    
    public InitialLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
