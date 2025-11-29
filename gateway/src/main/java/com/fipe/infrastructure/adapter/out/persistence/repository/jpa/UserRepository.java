package com.fipe.infrastructure.adapter.out.persistence.repository.jpa;

import com.fipe.infrastructure.adapter.out.persistence.entity.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserEntity> {
    
    public Optional<UserEntity> findByUsername(String username) {
        return find("username", username).firstResultOptional();
    }
    
    public Optional<UserEntity> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }
    
    public List<UserEntity> findAllActive() {
        return find("active", true).list();
    }
    
    public boolean existsByUsername(String username) {
        return count("username", username) > 0;
    }
    
    public boolean existsByEmail(String email) {
        return count("email", email) > 0;
    }
}
