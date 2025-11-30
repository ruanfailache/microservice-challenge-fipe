package com.fipe.domain.port.out.client;

import com.fipe.domain.enums.Role;
import com.fipe.infrastructure.adapter.in.rest.dto.response.UserResponse;
import com.fipe.infrastructure.adapter.out.rest.response.UserServiceResponse;

public interface UserClientPort {
    
    /**
     * Validates a JWT token with the user service
     * 
     * @param authorization the Authorization header value (Bearer token)
     * @return UserResponse if token is valid
     * @throws com.fipe.domain.exception.AuthenticationException if token is invalid
     */
    UserResponse validateToken(String authorization);
    
    /**
     * Validate user credentials via user service
     * 
     * @param username the username
     * @param password the password
     * @return the user's role if authentication is successful
     * @throws com.fipe.domain.exception.AuthenticationException if authentication fails
     */
    Role validateCredentials(String username, String password);
    
    /**
     * Get user by username
     * 
     * @param username the username
     * @return user response
     */
    UserServiceResponse getUserByUsername(String username);
}
