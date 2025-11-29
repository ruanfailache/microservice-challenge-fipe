package com.fipe.domain.port.in.usecase;

public interface RetryFailedMessagesUseCase {
    
    RetryResult retryEligibleFailures();
    
    class RetryResult {
        private final int successCount;
        private final int failedCount;
        private final int exhaustedCount;
        
        public RetryResult(int successCount, int failedCount, int exhaustedCount) {
            this.successCount = successCount;
            this.failedCount = failedCount;
            this.exhaustedCount = exhaustedCount;
        }
        
        public int getSuccessCount() { return successCount; }
        public int getFailedCount() { return failedCount; }
        public int getExhaustedCount() { return exhaustedCount; }
        public int getTotal() { return successCount + failedCount + exhaustedCount; }
    }
}
