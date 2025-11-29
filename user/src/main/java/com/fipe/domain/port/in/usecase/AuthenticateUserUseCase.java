package com.fipe.domain.port.in.usecase;

import com.fipe.domain.model.User;

/**
 * Use case for authenticating users
 */
public interface AuthenticateUserUseCase {
    
    /**
     * Authenticate a user with username and password
     * 
     * @param request the authentication request
     * @return the authenticated user
     */
    User execute(AuthenticationRequest request);
    
    /**
     * Request object for authenticating a user
     */
    record AuthenticationRequest(
        String username,
        String password
    ) {}
}
