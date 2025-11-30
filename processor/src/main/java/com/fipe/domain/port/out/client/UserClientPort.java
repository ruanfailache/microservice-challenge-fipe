package com.fipe.domain.port.out.client;

/**
 * Port for user client operations (token validation)
 */
public interface UserClientPort {
    
    /**
     * Validates a JWT token with the user service
     * 
     * @param authorization the Authorization header value (Bearer token)
     * @return true if token is valid
     * @throws com.fipe.domain.exception.ExternalServiceException if token validation fails
     */
    boolean validateToken(String authorization);
}

