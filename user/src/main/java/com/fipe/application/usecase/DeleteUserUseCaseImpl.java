package com.fipe.application.usecase;

import com.fipe.domain.exception.NotFoundException;
import com.fipe.domain.port.in.usecase.DeleteUserUseCase;
import com.fipe.domain.port.out.UserRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

/**
 * Implementation of DeleteUserUseCase
 */
@ApplicationScoped
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {
    
    private static final Logger LOG = Logger.getLogger(DeleteUserUseCaseImpl.class);
    
    @Inject
    UserRepositoryPort userRepository;
    
    @Override
    public void execute(Long id) {
        LOG.infof("Deleting user: %d", id);
        
        if (userRepository.findById(id).isEmpty()) {
            throw new NotFoundException("User not found: " + id);
        }
        
        boolean deleted = userRepository.deleteById(id);
        
        if (!deleted) {
            throw new RuntimeException("Failed to delete user: " + id);
        }
        
        LOG.infof("User deleted successfully: %d", id);
    }
}
