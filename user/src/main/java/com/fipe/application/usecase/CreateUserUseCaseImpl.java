package com.fipe.application.usecase;

import com.fipe.domain.enums.Role;
import com.fipe.domain.exception.ValidationException;
import com.fipe.domain.model.User;
import com.fipe.domain.port.in.usecase.CreateUserUseCase;
import com.fipe.domain.port.out.UserRepositoryPort;
import com.fipe.infrastructure.adapter.in.rest.dto.request.CreateUserRequest;
import com.fipe.infrastructure.security.PasswordEncoder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

/**
 * Implementation of CreateUserUseCase
 */
@ApplicationScoped
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    
    private static final Logger LOG = Logger.getLogger(CreateUserUseCaseImpl.class);
    
    @Inject
    UserRepositoryPort userRepository;
    
    @Inject
    PasswordEncoder passwordEncoder;
    
    @Override
    public User execute(CreateUserRequest request) {
        LOG.infof("Creating user: %s", request.username());
        
        validateRequest(request);
        checkDuplicates(request);
        
        String encodedPassword = passwordEncoder.encode(request.password());

        Role role = request.role() == null ? Role.USER : Role.fromString(request.role());

        User user = new User(
            request.username(),
            request.email(),
            encodedPassword,
            role
        );
        
        User savedUser = userRepository.save(user);
        LOG.infof("User created successfully: %s (ID: %d)", savedUser.getUsername(), savedUser.getId());
        
        return savedUser;
    }
    
    private void validateRequest(CreateUserRequest request) {
        if (request.username() == null || request.username().isBlank()) {
            throw new ValidationException("Username is required");
        }
        
        if (request.username().length() < 3 || request.username().length() > 50) {
            throw new ValidationException("Username must be between 3 and 50 characters");
        }
        
        if (request.email() == null || request.email().isBlank()) {
            throw new ValidationException("Email is required");
        }
        
        if (!request.email().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ValidationException("Invalid email format");
        }
        
        if (request.password() == null || request.password().length() < 6) {
            throw new ValidationException("Password must be at least 6 characters");
        }
    }
    
    private void checkDuplicates(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new ValidationException("Username already exists");
        }
        
        if (userRepository.existsByEmail(request.email())) {
            throw new ValidationException("Email already exists");
        }
    }
}
