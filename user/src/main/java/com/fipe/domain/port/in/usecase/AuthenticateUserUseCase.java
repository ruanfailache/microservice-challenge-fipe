package com.fipe.domain.port.in.usecase;

import com.fipe.domain.model.User;
import com.fipe.infrastructure.adapter.in.rest.dto.request.AuthenticationRequest;
import com.fipe.infrastructure.adapter.in.rest.dto.request.LoginRequest;

public interface AuthenticateUserUseCase {
    
    /**
     * Authenticate a user with username and password
     * 
     * @param request the authentication request
     * @return the authenticated user
     */
    User execute(LoginRequest request);
}
