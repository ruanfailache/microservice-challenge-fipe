package com.fipe.domain.port.in.usecase;

import com.fipe.domain.model.User;
import com.fipe.infrastructure.adapter.in.rest.dto.request.UpdateUserRequest;

public interface UpdateUserUseCase {
    
    /**
     * Update an existing user
     * 
     * @param id the user ID
     * @param request the update request
     * @return the updated user
     */
    User execute(Long id, UpdateUserRequest request);
}
