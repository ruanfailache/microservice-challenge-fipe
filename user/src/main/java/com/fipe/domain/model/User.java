package com.fipe.domain.model;

import java.time.LocalDateTime;

import com.fipe.domain.enums.Role;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Domain model representing a user in the system
 */
@Getter
@Setter
@ToString(of = {"id", "username", "email", "role", "active"})
@EqualsAndHashCode(of = {"id", "username"})
public class User {
    
    private Long id;
    private String username;
    private String email;
    private String passwordHash;
    private Role role;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;
    
    public User() {
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }
    
    public User(String username, String email, String passwordHash, Role role) {
        this();
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }
    
    public boolean hasRole(Role role) {
        return this.role != null && this.role.equals(role);
    }
    
    public boolean hasAnyRole(Role... roles) {
        if (this.role == null) {
            return false;
        }
        for (Role r : roles) {
            if (this.role.equals(r)) {
                return true;
            }
        }
        return false;
    }
}
