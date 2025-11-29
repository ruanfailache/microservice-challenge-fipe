package com.fipe.infrastructure.adapter.in.rest.health;

import com.fipe.domain.enums.FailureStatus;
import com.fipe.domain.port.out.repository.ProcessingFailureRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class ProcessingFailuresHealthCheck implements HealthCheck {
    
    private static final long MAX_FAILURES_THRESHOLD = 100;
    private static final String CHECK_NAME = "processing-failures";
    
    @Inject
    ProcessingFailureRepositoryPort repository;
    
    @Override
    public HealthCheckResponse call() {
        try {
            long count = repository.countByStatus(FailureStatus.MANUAL_REVIEW_REQUIRED);
            boolean isHealthy = count < MAX_FAILURES_THRESHOLD;
            
            return HealthCheckResponse.named(CHECK_NAME)
                    .status(isHealthy)
                    .withData("failures_requiring_review", count)
                    .withData("threshold", MAX_FAILURES_THRESHOLD)
                    .build();
                    
        } catch (Exception e) {
            return HealthCheckResponse.named(CHECK_NAME)
                    .down()
                    .withData("error", e.getMessage())
                    .build();
        }
    }
}
