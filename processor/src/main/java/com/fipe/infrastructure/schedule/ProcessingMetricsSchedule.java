package com.fipe.infrastructure.schedule;

import com.fipe.domain.enums.FailureStatus;
import com.fipe.domain.port.out.repository.ProcessingFailureRepositoryPort;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ProcessingMetricsSchedule {
    
    private static final Logger LOG = Logger.getLogger(ProcessingMetricsSchedule.class);
    private static final String METRIC_PREFIX = "processing.failures";
    
    @Inject
    MeterRegistry registry;
    
    @Inject
    ProcessingFailureRepositoryPort repository;
    
    @Scheduled(every = "30s")
    public void updateMetrics() {
        try {
            registerGauge("pending_retry", FailureStatus.PENDING_RETRY);
            registerGauge("retry_exhausted", FailureStatus.RETRY_EXHAUSTED);
            registerGauge("manual_review", FailureStatus.MANUAL_REVIEW_REQUIRED);
            registerGauge("resolved", FailureStatus.RESOLVED);
            LOG.debug("Updated failure metrics");
        } catch (Exception e) {
            LOG.errorf(e, "Error updating metrics");
        }
    }
    
    private void registerGauge(String name, FailureStatus status) {
        long count = repository.countByStatus(status);
        Tags tags = Tags.of(Tag.of("status", status.name()));
        registry.gauge(METRIC_PREFIX + "." + name, tags, count);
    }
}
