package com.fipe.application.usecase;

import com.fipe.domain.exception.AuthenticationException;
import com.fipe.domain.exception.NotFoundException;
import com.fipe.domain.exception.ValidationException;
import com.fipe.domain.model.User;
import com.fipe.domain.port.in.usecase.ChangePasswordUseCase;
import com.fipe.domain.port.out.UserRepositoryPort;
import com.fipe.infrastructure.security.PasswordEncoder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;

/**
 * Implementation of ChangePasswordUseCase
 */
@ApplicationScoped
public class ChangePasswordUseCaseImpl implements ChangePasswordUseCase {
    
    private static final Logger LOG = Logger.getLogger(ChangePasswordUseCaseImpl.class);
    
    @Inject
    UserRepositoryPort userRepository;
    
    @Inject
    PasswordEncoder passwordEncoder;
    
    @Override
    public void execute(Long id, ChangePasswordRequest request) {
        LOG.infof("Changing password for user: %d", id);
        
        validateRequest(request);
        
        User user = userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("User not found: " + id));
        
        if (!passwordEncoder.matches(request.currentPassword(), user.getPasswordHash())) {
            throw new AuthenticationException("Current password is incorrect");
        }
        
        String newPasswordHash = passwordEncoder.encode(request.newPassword());
        user.setPasswordHash(newPasswordHash);
        user.setUpdatedAt(LocalDateTime.now());
        
        userRepository.update(user);
        
        LOG.infof("Password changed successfully for user: %d", id);
    }
    
    private void validateRequest(ChangePasswordRequest request) {
        if (request.currentPassword() == null || request.currentPassword().isBlank()) {
            throw new ValidationException("Current password is required");
        }
        
        if (request.newPassword() == null || request.newPassword().length() < 6) {
            throw new ValidationException("New password must be at least 6 characters");
        }
        
        if (request.currentPassword().equals(request.newPassword())) {
            throw new ValidationException("New password must be different from current password");
        }
    }
}
