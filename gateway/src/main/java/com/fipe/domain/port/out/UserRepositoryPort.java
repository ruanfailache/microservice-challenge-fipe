package com.fipe.domain.port.out;

import com.fipe.domain.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Port for User repository operations
 */
public interface UserRepositoryPort {
    
    /**
     * Save a user
     * 
     * @param user the user to save
     * @return the saved user with generated ID
     */
    User save(User user);
    
    /**
     * Update a user
     * 
     * @param user the user to update
     * @return the updated user
     */
    User update(User user);
    
    /**
     * Find a user by ID
     * 
     * @param id the user ID
     * @return an Optional containing the user if found
     */
    Optional<User> findById(Long id);
    
    /**
     * Find a user by username
     * 
     * @param username the username
     * @return an Optional containing the user if found
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Find a user by email
     * 
     * @param email the email
     * @return an Optional containing the user if found
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Find all users
     * 
     * @return list of all users
     */
    List<User> findAll();
    
    /**
     * Find all active users
     * 
     * @return list of active users
     */
    List<User> findAllActive();
    
    /**
     * Delete a user by ID
     * 
     * @param id the user ID
     * @return true if deleted, false otherwise
     */
    boolean deleteById(Long id);
    
    /**
     * Check if a username exists
     * 
     * @param username the username to check
     * @return true if exists, false otherwise
     */
    boolean existsByUsername(String username);
    
    /**
     * Check if an email exists
     * 
     * @param email the email to check
     * @return true if exists, false otherwise
     */
    boolean existsByEmail(String email);
    
    /**
     * Count total users
     * 
     * @return the total number of users
     */
    long count();
}
