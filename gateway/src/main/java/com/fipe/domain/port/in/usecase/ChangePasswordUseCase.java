package com.fipe.domain.port.in.usecase;

/**
 * Use case for changing user password
 */
public interface ChangePasswordUseCase {
    
    /**
     * Change user password
     * 
     * @param id the user ID
     * @param request the password change request
     */
    void execute(Long id, ChangePasswordRequest request);
    
    /**
     * Request object for changing password
     */
    record ChangePasswordRequest(
        String currentPassword,
        String newPassword
    ) {}
}
