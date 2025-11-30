package com.fipe.infrastructure.adapter.out.persistence.repository.adapter;

import com.fipe.domain.model.User;
import com.fipe.domain.port.out.UserRepositoryPort;
import com.fipe.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.fipe.infrastructure.adapter.out.persistence.mapper.UserMapper;
import com.fipe.infrastructure.adapter.out.persistence.repository.jpa.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * JPA implementation of UserRepositoryPort
 */
@ApplicationScoped
public class UserRepositoryAdapter implements UserRepositoryPort {
    
    @Inject
    UserRepository userRepository;
    
    @Inject
    UserMapper userMapper;
    
    @Override
    @Transactional
    public User save(User user) {
        UserEntity entity = userMapper.toEntity(user);
        userRepository.persist(entity);
        return userMapper.toDomain(entity);
    }
    
    @Override
    @Transactional
    public User update(User user) {
        UserEntity entity = userMapper.toEntity(user);
        entity = userRepository.getEntityManager().merge(entity);
        return userMapper.toDomain(entity);
    }
    
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findByIdOptional(id).map(userMapper::toDomain);
    }
    
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDomain);
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }
    
    @Override
    public List<User> findAll() {
        return userRepository.listAll().stream()
                .map(userMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<User> findAllActive() {
        return userRepository.findAllActive().stream()
                .map(userMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public boolean deleteById(Long id) {
        return userRepository.deleteById(id);
    }
    
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    @Override
    public long count() {
        return userRepository.count();
    }
}
