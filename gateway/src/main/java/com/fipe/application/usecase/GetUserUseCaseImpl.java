package com.fipe.application.usecase;

import com.fipe.domain.exception.NotFoundException;
import com.fipe.domain.model.User;
import com.fipe.domain.port.in.usecase.GetUserUseCase;
import com.fipe.domain.port.out.UserRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

/**
 * Implementation of GetUserUseCase
 */
@ApplicationScoped
public class GetUserUseCaseImpl implements GetUserUseCase {
    
    private static final Logger LOG = Logger.getLogger(GetUserUseCaseImpl.class);
    
    @Inject
    UserRepositoryPort userRepository;
    
    @Override
    public User getById(Long id) {
        LOG.debugf("Getting user by ID: %d", id);
        return userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("User not found: " + id));
    }
    
    @Override
    public User getByUsername(String username) {
        LOG.debugf("Getting user by username: %s", username);
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("User not found: " + username));
    }
    
    @Override
    public List<User> getAll() {
        LOG.debug("Getting all users");
        return userRepository.findAll();
    }
    
    @Override
    public List<User> getAllActive() {
        LOG.debug("Getting all active users");
        return userRepository.findAllActive();
    }
}
