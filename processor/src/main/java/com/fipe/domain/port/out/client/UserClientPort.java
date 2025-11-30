package com.fipe.domain.port.out.client;

import com.fipe.infrastructure.adapter.in.rest.dto.response.UserResponse;

/**
 * Port for user client operations (token validation)
 */
public interface UserClientPort {
    
    /**
     * Validates a JWT token with the user service
     * 
     * @param authorization the Authorization header value (Bearer token)
     * @return UserResponse if token is valid
     * @throws com.fipe.domain.exception.AuthenticationException if token is invalid
     */
    UserResponse validateToken(String authorization);
}
