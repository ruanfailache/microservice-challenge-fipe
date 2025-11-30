package com.fipe.domain.port.in.usecase;

import com.fipe.domain.model.User;

import java.util.List;

public interface GetUserUseCase {
    
    /**
     * Get a user by ID
     * 
     * @param id the user ID
     * @return the user
     */
    User getById(Long id);
    
    /**
     * Get a user by username
     * 
     * @param username the username
     * @return the user
     */
    User getByUsername(String username);
    
    /**
     * Get all users
     * 
     * @return list of all users
     */
    List<User> getAll();
    
    /**
     * Get all active users
     * 
     * @return list of active users
     */
    List<User> getAllActive();
}
