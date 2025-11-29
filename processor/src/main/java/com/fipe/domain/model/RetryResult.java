package com.fipe.domain.model;

import lombok.Getter;

@Getter
public class RetryResult {
    private final int successCount;
    private final int failedCount;
    private final int exhaustedCount;
    
    public RetryResult(int successCount, int failedCount, int exhaustedCount) {
        this.successCount = successCount;
        this.failedCount = failedCount;
        this.exhaustedCount = exhaustedCount;
    }
    
    public int getTotal() {
        return successCount + failedCount + exhaustedCount;
    }
}
