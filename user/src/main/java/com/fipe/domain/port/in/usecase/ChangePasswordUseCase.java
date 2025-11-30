package com.fipe.domain.port.in.usecase;

import com.fipe.infrastructure.adapter.in.rest.dto.request.ChangePasswordRequest;

public interface ChangePasswordUseCase {
    
    /**
     * Change user password
     * 
     * @param id the user ID
     * @param request the password change request
     */
    void execute(Long id, ChangePasswordRequest request);
}
