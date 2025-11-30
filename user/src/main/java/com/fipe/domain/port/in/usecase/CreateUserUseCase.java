package com.fipe.domain.port.in.usecase;

import com.fipe.domain.model.User;
import com.fipe.infrastructure.adapter.in.rest.dto.request.CreateUserRequest;

public interface CreateUserUseCase {
    
    /**
     * Create a new user
     * 
     * @param request the user creation request
     * @return the created user
     */
    User execute(CreateUserRequest request);
}
