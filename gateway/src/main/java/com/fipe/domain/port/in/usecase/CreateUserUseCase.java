package com.fipe.domain.port.in.usecase;

import com.fipe.domain.model.User;

/**
 * Use case for creating a new user
 */
public interface CreateUserUseCase {
    
    /**
     * Create a new user
     * 
     * @param request the user creation request
     * @return the created user
     */
    User execute(CreateUserRequest request);
    
    /**
     * Request object for creating a user
     */
    record CreateUserRequest(
        String username,
        String email,
        String password,
        com.fipe.domain.enums.Role role
    ) {}
}
