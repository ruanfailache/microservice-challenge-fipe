package com.fipe.application.usecase;

import com.fipe.domain.exception.AuthenticationException;
import com.fipe.domain.exception.NotFoundException;
import com.fipe.domain.model.User;
import com.fipe.domain.port.in.usecase.AuthenticateUserUseCase;
import com.fipe.domain.port.out.UserRepositoryPort;
import com.fipe.infrastructure.adapter.in.rest.dto.request.AuthenticationRequest;
import com.fipe.infrastructure.adapter.in.rest.dto.request.LoginRequest;
import com.fipe.infrastructure.security.service.PasswordEncoderService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;

/**
 * Implementation of AuthenticateUserUseCase
 */
@ApplicationScoped
public class AuthenticateUserUseCaseImpl implements AuthenticateUserUseCase {
    
    private static final Logger LOG = Logger.getLogger(AuthenticateUserUseCaseImpl.class);
    
    @Inject
    UserRepositoryPort userRepository;
    
    @Inject
    PasswordEncoderService passwordEncoderService;
    
    @Override
    public User execute(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
            .orElseThrow(() -> new NotFoundException("User not found: " + request.username()));
        
        if (!user.isActive()) {
            throw new AuthenticationException("User account is inactive");
        }
        
        if (!passwordEncoderService.matches(request.password(), user.getPasswordHash())) {
            throw new AuthenticationException("Invalid credentials");
        }

        user.setLastLoginAt(LocalDateTime.now());
        userRepository.update(user);
        
        LOG.infof("User authenticated successfully: %s", request.username());
        return user;
    }
}
