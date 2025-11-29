package com.fipe.application.usecase;

import com.fipe.domain.enums.FailureStatus;
import com.fipe.domain.exception.ResourceNotFoundException;
import com.fipe.domain.model.ProcessingFailure;
import com.fipe.domain.port.in.usecase.ManageProcessingFailureUseCase;
import com.fipe.domain.port.out.repository.ProcessingFailureRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ManageProcessingFailureUseCaseImpl implements ManageProcessingFailureUseCase {
    
    private static final Logger LOG = Logger.getLogger(ManageProcessingFailureUseCaseImpl.class);
    
    @Inject
    ProcessingFailureRepositoryPort repository;
    
    @Override
    @Transactional
    public ProcessingFailure markForRetry(Long failureId) {
        ProcessingFailure failure = repository.findById(failureId)
                .orElseThrow(() -> new ResourceNotFoundException("Failure not found with id: " + failureId));
        
        failure.resetForRetry();
        ProcessingFailure updated = repository.save(failure);
        
        LOG.infof("Marked failure ID %d for retry", failureId);
        return updated;
    }
    
    @Override
    @Transactional
    public ProcessingFailure markAsResolved(Long failureId) {
        ProcessingFailure failure = repository.findById(failureId)
                .orElseThrow(() -> new ResourceNotFoundException("Failure not found with id: " + failureId));
        
        failure.setStatus(FailureStatus.RESOLVED);
        ProcessingFailure updated = repository.save(failure);
        
        LOG.infof("Marked failure ID %d as resolved", failureId);
        return updated;
    }
    
    @Override
    public List<ProcessingFailure> findByStatus(String status) {
        FailureStatus failureStatus = FailureStatus.valueOf(status.toUpperCase());
        return repository.findByStatus(failureStatus);
    }
    
    @Override
    public ProcessingFailure findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Failure not found with id: " + id));
    }
    
    @Override
    public List<ProcessingFailure> findByBrandCode(String brandCode) {
        return repository.findByBrandCode(brandCode);
    }
    
    @Override
    public FailureStatistics getStatistics() {
        long pendingRetry = repository.countByStatus(FailureStatus.PENDING_RETRY);
        long retryExhausted = repository.countByStatus(FailureStatus.RETRY_EXHAUSTED);
        long manualReview = repository.countByStatus(FailureStatus.MANUAL_REVIEW_REQUIRED);
        long resolved = repository.countByStatus(FailureStatus.RESOLVED);
        
        return new FailureStatistics(pendingRetry, retryExhausted, manualReview, resolved);
    }
    
    @Override
    @Transactional
    public long cleanupResolvedFailures(int daysOld) {
        LocalDateTime beforeDate = LocalDateTime.now().minusDays(daysOld);
        long deleted = repository.deleteOldResolved(beforeDate);
        
        LOG.infof("Cleaned up %d resolved failures older than %d days", deleted, daysOld);
        return deleted;
    }
}
