package com.fipe.domain.port.in.usecase;

/**
 * Use case for deleting a user
 */
public interface DeleteUserUseCase {
    
    /**
     * Delete a user by ID
     * 
     * @param id the user ID
     */
    void execute(Long id);
}
