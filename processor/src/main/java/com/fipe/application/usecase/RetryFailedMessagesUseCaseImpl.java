package com.fipe.application.usecase;

import com.fipe.domain.enums.FailureStatus;
import com.fipe.domain.model.ProcessingFailure;
import com.fipe.domain.port.in.usecase.ProcessVehicleDataUseCase;
import com.fipe.domain.port.in.usecase.RetryFailedMessagesUseCase;
import com.fipe.domain.port.out.repository.ProcessingFailureRepositoryPort;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class RetryFailedMessagesUseCaseImpl implements RetryFailedMessagesUseCase {
    
    private static final Logger LOG = Logger.getLogger(RetryFailedMessagesUseCaseImpl.class);
    private static final int MAX_RETRY_ATTEMPTS = 5;
    private static final int RETRY_DELAY_MINUTES = 15;
    
    @Inject
    ProcessingFailureRepositoryPort repository;
    
    @Inject
    ProcessVehicleDataUseCase processVehicleDataUseCase;
    
    @Scheduled(every = "15m", delayed = "5m")
    public void scheduleRetry() {
        retryEligibleFailures();
    }
    
    @Override
    @Transactional
    public com.fipe.domain.model.RetryResult retryEligibleFailures() {
        LOG.info("Starting retry of eligible failed messages...");
        
        LocalDateTime retryThreshold = LocalDateTime.now().minusMinutes(RETRY_DELAY_MINUTES);
        List<ProcessingFailure> eligibleFailures = repository.findEligibleForRetry(retryThreshold);
        
        if (eligibleFailures.isEmpty()) {
            LOG.info("No failed messages eligible for retry at this time.");
            return new com.fipe.domain.model.RetryResult(0, 0, 0);
        }
        
        LOG.infof("Found %d failures eligible for retry", eligibleFailures.size());
        
        int successCount = 0;
        int failedCount = 0;
        int exhaustedCount = 0;
        
        for (ProcessingFailure failure : eligibleFailures) {
            if (failure.getRetryCount() >= MAX_RETRY_ATTEMPTS) {
                failure.setStatus(FailureStatus.RETRY_EXHAUSTED);
                repository.save(failure);
                exhaustedCount++;
                LOG.warnf("Max retry attempts reached for brand: %s", failure.getBrandCode());
                continue;
            }
            
            try {
                LOG.infof("Retrying brand: %s (attempt %d/%d)",
                        failure.getBrandCode(), failure.getRetryCount() + 1, MAX_RETRY_ATTEMPTS);
                
                processVehicleDataUseCase.processVehicleData(
                        failure.getBrandCode(),
                        failure.getBrandName()
                );
                
                failure.setStatus(FailureStatus.RESOLVED);
                repository.save(failure);
                successCount++;
                
                LOG.infof("Successfully retried brand: %s", failure.getBrandCode());
                
            } catch (Exception e) {
                failure.incrementRetryCount();
                failure.setFailureReason(e.getMessage());
                failure.setStackTrace(extractStackTrace(e));
                repository.save(failure);
                failedCount++;
                
                LOG.errorf(e, "Retry failed for brand: %s", failure.getBrandCode());
            }
        }
        
        LOG.infof("Retry completed - Success: %d, Failed: %d, Exhausted: %d",
                successCount, failedCount, exhaustedCount);
        
        return new com.fipe.domain.model.RetryResult(successCount, failedCount, exhaustedCount);
    }
    
    private String extractStackTrace(Exception e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.getClass().getName()).append(": ").append(e.getMessage()).append("\n");
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append("\tat ").append(element.toString()).append("\n");
            if (sb.length() > 2000) {
                sb.append("... (truncated)");
                break;
            }
        }
        return sb.toString();
    }
}
