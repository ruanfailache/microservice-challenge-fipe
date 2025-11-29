package com.fipe.domain.model;

import com.fipe.domain.enums.FailureStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ProcessingFailure {
    
    private Long id;
    private String brandCode;
    private String brandName;
    private String failureReason;
    private String stackTrace;
    private Integer retryCount;
    private String kafkaTopic;
    private Integer kafkaPartition;
    private Long kafkaOffset;
    private LocalDateTime createdAt;
    private LocalDateTime lastAttemptAt;
    private FailureStatus status;
    
    public ProcessingFailure(String brandCode, String brandName, String failureReason, String stackTrace, FailureStatus status) {
        this.brandCode = brandCode;
        this.brandName = brandName;
        this.failureReason = failureReason;
        this.stackTrace = stackTrace;
        this.status = status;
        this.retryCount = 0;
        this.createdAt = LocalDateTime.now();
        this.lastAttemptAt = LocalDateTime.now();
    }
    
    public void incrementRetryCount() {
        this.retryCount++;
        this.lastAttemptAt = LocalDateTime.now();
    }
    
    public void resetForRetry() {
        this.retryCount = 0;
        this.status = FailureStatus.PENDING_RETRY;
    }
}
