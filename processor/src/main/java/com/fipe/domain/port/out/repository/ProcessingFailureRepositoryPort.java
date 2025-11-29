package com.fipe.domain.port.out.repository;

import com.fipe.domain.enums.FailureStatus;
import com.fipe.domain.model.ProcessingFailure;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProcessingFailureRepositoryPort {
    
    ProcessingFailure save(ProcessingFailure failure);
    
    Optional<ProcessingFailure> findById(Long id);
    
    List<ProcessingFailure> findByStatus(FailureStatus status);
    
    List<ProcessingFailure> findEligibleForRetry(LocalDateTime beforeTime);
    
    List<ProcessingFailure> findByBrandCode(String brandCode);
    
    long countByStatus(FailureStatus status);
    
    long deleteOldResolved(LocalDateTime beforeDate);
}
