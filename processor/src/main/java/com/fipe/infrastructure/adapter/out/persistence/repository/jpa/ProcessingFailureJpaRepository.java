package com.fipe.infrastructure.adapter.out.persistence.repository.jpa;

import com.fipe.infrastructure.adapter.out.persistence.entity.ProcessingFailureEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ProcessingFailureJpaRepository implements PanacheRepository<ProcessingFailureEntity> {
    
    public List<ProcessingFailureEntity> findByStatus(String status) {
        return find("status", status).list();
    }
    
    public List<ProcessingFailureEntity> findEligibleForRetry(String status, LocalDateTime beforeTime) {
        return find("status = ?1 AND lastAttemptAt < ?2", status, beforeTime).list();
    }
    
    public List<ProcessingFailureEntity> findByBrandCode(String brandCode) {
        return find("brandCode", brandCode).list();
    }
    
    public long countByStatus(String status) {
        return count("status", status);
    }
    
    public long deleteByStatusAndCreatedAtBefore(String status, LocalDateTime beforeDate) {
        return delete("status = ?1 AND createdAt < ?2", status, beforeDate);
    }
}
