package com.fipe.domain.model;

import com.fipe.domain.enums.FailureStatus;

import java.time.LocalDateTime;

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
    
    public ProcessingFailure() {
        this.retryCount = 0;
        this.createdAt = LocalDateTime.now();
        this.lastAttemptAt = LocalDateTime.now();
    }
    
    public ProcessingFailure(String brandCode, String brandName, String failureReason, 
                           String stackTrace, FailureStatus status) {
        this();
        this.brandCode = brandCode;
        this.brandName = brandName;
        this.failureReason = failureReason;
        this.stackTrace = stackTrace;
        this.status = status;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getBrandCode() { return brandCode; }
    public void setBrandCode(String brandCode) { this.brandCode = brandCode; }
    
    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName; }
    
    public String getFailureReason() { return failureReason; }
    public void setFailureReason(String failureReason) { this.failureReason = failureReason; }
    
    public String getStackTrace() { return stackTrace; }
    public void setStackTrace(String stackTrace) { this.stackTrace = stackTrace; }
    
    public Integer getRetryCount() { return retryCount; }
    public void setRetryCount(Integer retryCount) { this.retryCount = retryCount; }
    
    public String getKafkaTopic() { return kafkaTopic; }
    public void setKafkaTopic(String kafkaTopic) { this.kafkaTopic = kafkaTopic; }
    
    public Integer getKafkaPartition() { return kafkaPartition; }
    public void setKafkaPartition(Integer kafkaPartition) { this.kafkaPartition = kafkaPartition; }
    
    public Long getKafkaOffset() { return kafkaOffset; }
    public void setKafkaOffset(Long kafkaOffset) { this.kafkaOffset = kafkaOffset; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getLastAttemptAt() { return lastAttemptAt; }
    public void setLastAttemptAt(LocalDateTime lastAttemptAt) { this.lastAttemptAt = lastAttemptAt; }
    
    public FailureStatus getStatus() { return status; }
    public void setStatus(FailureStatus status) { this.status = status; }
    
    public void incrementRetryCount() {
        this.retryCount++;
        this.lastAttemptAt = LocalDateTime.now();
    }
    
    public void resetForRetry() {
        this.retryCount = 0;
        this.status = FailureStatus.PENDING_RETRY;
    }
}
