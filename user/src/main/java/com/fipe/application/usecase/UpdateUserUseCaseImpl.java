package com.fipe.application.usecase;

import com.fipe.domain.enums.Role;
import com.fipe.domain.exception.NotFoundException;
import com.fipe.domain.exception.ValidationException;
import com.fipe.domain.model.User;
import com.fipe.domain.port.in.usecase.UpdateUserUseCase;
import com.fipe.domain.port.out.UserRepositoryPort;
import com.fipe.infrastructure.adapter.in.rest.dto.request.UpdateUserRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;

@ApplicationScoped
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    
    private static final Logger LOG = Logger.getLogger(UpdateUserUseCaseImpl.class);
    
    @Inject
    UserRepositoryPort userRepository;
    
    @Override
    public User execute(Long id, UpdateUserRequest request) {
        LOG.infof("Updating user: %d", id);
        
        User user = userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("User not found: " + id));
        
        if (request.email() != null) {
            validateEmail(request.email());
            checkEmailDuplicate(request.email(), id);
            user.setEmail(request.email());
        }
        
        if (request.role() != null) {
            Role role = Role.fromString(request.role());
            user.setRole(role);
        }
        
        if (request.active() != null) {
            user.setActive(request.active());
        }
        
        user.setUpdatedAt(LocalDateTime.now());
        
        User updatedUser = userRepository.update(user);
        LOG.infof("User updated successfully: %s (ID: %d)", updatedUser.getUsername(), updatedUser.getId());
        
        return updatedUser;
    }
    
    private void validateEmail(String email) {
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ValidationException("Invalid email format");
        }
    }
    
    private void checkEmailDuplicate(String email, Long currentUserId) {
        userRepository.findByEmail(email).ifPresent(existingUser -> {
            if (!existingUser.getId().equals(currentUserId)) {
                throw new ValidationException("Email already exists");
            }
        });
    }
}
