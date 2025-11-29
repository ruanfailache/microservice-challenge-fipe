package com.fipe.domain.port.in.usecase;

import com.fipe.domain.model.User;

/**
 * Use case for updating an existing user
 */
public interface UpdateUserUseCase {
    
    /**
     * Update an existing user
     * 
     * @param id the user ID
     * @param request the update request
     * @return the updated user
     */
    User execute(Long id, UpdateUserRequest request);
    
    /**
     * Request object for updating a user
     */
    record UpdateUserRequest(
        String email,
        com.fipe.domain.enums.Role role,
        Boolean active
    ) {}
}
