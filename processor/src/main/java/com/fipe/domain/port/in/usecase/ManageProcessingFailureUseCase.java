package com.fipe.domain.port.in.usecase;

import com.fipe.domain.model.ProcessingFailure;

import java.util.List;

public interface ManageProcessingFailureUseCase {
    
    ProcessingFailure markForRetry(Long failureId);
    
    ProcessingFailure markAsResolved(Long failureId);
    
    List<ProcessingFailure> findByStatus(String status);
    
    ProcessingFailure findById(Long id);
    
    List<ProcessingFailure> findByBrandCode(String brandCode);
    
    FailureStatistics getStatistics();
    
    long cleanupResolvedFailures(int daysOld);
    
    class FailureStatistics {
        private final long pendingRetry;
        private final long retryExhausted;
        private final long manualReview;
        private final long resolved;
        private final long total;
        
        public FailureStatistics(long pendingRetry, long retryExhausted, long manualReview, long resolved) {
            this.pendingRetry = pendingRetry;
            this.retryExhausted = retryExhausted;
            this.manualReview = manualReview;
            this.resolved = resolved;
            this.total = pendingRetry + retryExhausted + manualReview + resolved;
        }
        
        public long getPendingRetry() { return pendingRetry; }
        public long getRetryExhausted() { return retryExhausted; }
        public long getManualReview() { return manualReview; }
        public long getResolved() { return resolved; }
        public long getTotal() { return total; }
    }
}
