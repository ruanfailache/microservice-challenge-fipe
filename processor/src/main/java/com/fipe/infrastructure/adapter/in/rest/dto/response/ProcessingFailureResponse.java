package com.fipe.infrastructure.adapter.in.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessingFailureResponse {
    
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
    private String status;
}
