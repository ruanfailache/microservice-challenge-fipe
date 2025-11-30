package com.fipe.infrastructure.adapter.out.persistence.repository.adapter;

import com.fipe.domain.enums.FailureStatus;
import com.fipe.domain.model.ProcessingFailure;
import com.fipe.domain.port.out.repository.ProcessingFailureRepositoryPort;
import com.fipe.infrastructure.adapter.out.persistence.mapper.ProcessingFailureMapper;
import com.fipe.infrastructure.adapter.out.persistence.repository.jpa.ProcessingFailureJpaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProcessingFailureRepositoryAdapter implements ProcessingFailureRepositoryPort {
    
    @Inject
    ProcessingFailureJpaRepository jpaRepository;
    
    @Inject
    ProcessingFailureMapper processingFailureMapper;
    
    @Override
    @Transactional
    public ProcessingFailure save(ProcessingFailure failure) {
        var entity = processingFailureMapper.toEntity(failure);
        jpaRepository.persist(entity);
        return processingFailureMapper.toDomain(entity);
    }
    
    @Override
    public Optional<ProcessingFailure> findById(Long id) {
        return jpaRepository.findByIdOptional(id)
                .map(processingFailureMapper::toDomain);
    }
    
    @Override
    public List<ProcessingFailure> findByStatus(FailureStatus status) {
        return jpaRepository.findByStatus(status.name()).stream()
                .map(processingFailureMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ProcessingFailure> findEligibleForRetry(LocalDateTime beforeTime) {
        return jpaRepository.findEligibleForRetry(
                FailureStatus.PENDING_RETRY.name(), 
                beforeTime
        ).stream()
                .map(processingFailureMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ProcessingFailure> findByBrandCode(String brandCode) {
        return jpaRepository.findByBrandCode(brandCode).stream()
                .map(processingFailureMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public long countByStatus(FailureStatus status) {
        return jpaRepository.countByStatus(status.name());
    }
    
    @Override
    @Transactional
    public long deleteOldResolved(LocalDateTime beforeDate) {
        return jpaRepository.deleteByStatusAndCreatedAtBefore(
                FailureStatus.RESOLVED.name(), 
                beforeDate
        );
    }
}
