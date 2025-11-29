package com.fipe.infrastructure.adapter.in.rest.mapper;

import com.fipe.domain.model.FailureStatistics;
import com.fipe.domain.model.ProcessingFailure;
import com.fipe.infrastructure.adapter.in.rest.dto.response.FailureStatisticsResponse;
import com.fipe.infrastructure.adapter.in.rest.dto.response.ProcessingFailureResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ProcessingFailureRestMapper {
    
    private ProcessingFailureRestMapper() {
        // Utility class
    }
    
    public static ProcessingFailureResponse toResponse(ProcessingFailure failure) {
        if (failure == null) {
            return null;
        }
        
        return new ProcessingFailureResponse(
                failure.getId(),
                failure.getBrandCode(),
                failure.getBrandName(),
                failure.getFailureReason(),
                failure.getStackTrace(),
                failure.getRetryCount(),
                failure.getKafkaTopic(),
                failure.getKafkaPartition(),
                failure.getKafkaOffset(),
                failure.getCreatedAt(),
                failure.getLastAttemptAt(),
                failure.getStatus() != null ? failure.getStatus().name() : null
        );
    }
    
    public static List<ProcessingFailureResponse> toResponseList(List<ProcessingFailure> failures) {
        return failures.stream()
                .map(ProcessingFailureRestMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    public static FailureStatisticsResponse toResponse(FailureStatistics stats) {
        if (stats == null) {
            return null;
        }
        
        return new FailureStatisticsResponse(
                stats.getPendingRetry(),
                stats.getRetryExhausted(),
                stats.getManualReview(),
                stats.getResolved(),
                stats.getTotal()
        );
    }
}
