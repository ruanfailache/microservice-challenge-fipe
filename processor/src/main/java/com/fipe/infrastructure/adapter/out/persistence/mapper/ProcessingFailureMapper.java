package com.fipe.infrastructure.adapter.out.persistence.mapper;

import com.fipe.domain.enums.FailureStatus;
import com.fipe.domain.model.ProcessingFailure;
import com.fipe.infrastructure.adapter.out.persistence.entity.ProcessingFailureEntity;

public class ProcessingFailureMapper {
    
    private ProcessingFailureMapper() {
        throw new UnsupportedOperationException("Utility class");
    }
    
    public static ProcessingFailureEntity toEntity(ProcessingFailure domain) {
        if (domain == null) {
            return null;
        }
        
        ProcessingFailureEntity entity = new ProcessingFailureEntity();
        entity.setId(domain.getId());
        entity.setBrandCode(domain.getBrandCode());
        entity.setBrandName(domain.getBrandName());
        entity.setFailureReason(domain.getFailureReason());
        entity.setStackTrace(domain.getStackTrace());
        entity.setRetryCount(domain.getRetryCount());
        entity.setKafkaTopic(domain.getKafkaTopic());
        entity.setKafkaPartition(domain.getKafkaPartition());
        entity.setKafkaOffset(domain.getKafkaOffset());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setLastAttemptAt(domain.getLastAttemptAt());
        entity.setStatus(domain.getStatus() != null ? domain.getStatus().name() : null);
        
        return entity;
    }
    
    public static ProcessingFailure toDomain(ProcessingFailureEntity entity) {
        if (entity == null) {
            return null;
        }
        
        ProcessingFailure domain = new ProcessingFailure();
        domain.setId(entity.getId());
        domain.setBrandCode(entity.getBrandCode());
        domain.setBrandName(entity.getBrandName());
        domain.setFailureReason(entity.getFailureReason());
        domain.setStackTrace(entity.getStackTrace());
        domain.setRetryCount(entity.getRetryCount());
        domain.setKafkaTopic(entity.getKafkaTopic());
        domain.setKafkaPartition(entity.getKafkaPartition());
        domain.setKafkaOffset(entity.getKafkaOffset());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setLastAttemptAt(entity.getLastAttemptAt());
        domain.setStatus(entity.getStatus() != null ? FailureStatus.valueOf(entity.getStatus()) : null);
        
        return domain;
    }
}
