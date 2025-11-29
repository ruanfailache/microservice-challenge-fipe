package com.fipe.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "processing_failures")
@Getter
@Setter
public class ProcessingFailureEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "brand_code", nullable = false)
    private String brandCode;
    
    @Column(name = "brand_name", nullable = false)
    private String brandName;
    
    @Column(name = "failure_reason", columnDefinition = "TEXT")
    private String failureReason;
    
    @Column(name = "stack_trace", columnDefinition = "TEXT")
    private String stackTrace;
    
    @Column(name = "retry_count", nullable = false)
    private Integer retryCount;
    
    @Column(name = "kafka_topic")
    private String kafkaTopic;
    
    @Column(name = "kafka_partition")
    private Integer kafkaPartition;
    
    @Column(name = "kafka_offset")
    private Long kafkaOffset;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "last_attempt_at")
    private LocalDateTime lastAttemptAt;
    
    @Column(name = "status", nullable = false)
    private String status;
}
