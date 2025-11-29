package com.fipe.domain.model;

import lombok.Getter;

@Getter
public class FailureStatistics {
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
}
