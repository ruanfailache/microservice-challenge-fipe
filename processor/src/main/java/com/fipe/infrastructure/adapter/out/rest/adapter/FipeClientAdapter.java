package com.fipe.infrastructure.adapter.out.rest.adapter;

import com.fipe.domain.exception.ExternalServiceException;
import com.fipe.domain.model.Brand;
import com.fipe.domain.model.Model;
import com.fipe.domain.port.out.client.FipeClientPort;
import com.fipe.domain.port.out.publisher.DlqPublisherPort;
import com.fipe.infrastructure.adapter.out.rest.client.FipeClient;
import com.fipe.infrastructure.adapter.out.rest.response.FipeModelsWrapper;
import com.fipe.infrastructure.adapter.in.messaging.message.VehicleDataMessage;
import io.smallrye.faulttolerance.api.CircuitBreakerName;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.faulttolerance.*;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class FipeClientAdapter implements FipeClientPort {
    
    private static final Logger LOG = Logger.getLogger(FipeClientAdapter.class);
    
    @Inject
    @RestClient
    FipeClient fipeClient;

    @Inject
    DlqPublisherPort dlqPublisher;

    @Retry(delay = 2, delayUnit = ChronoUnit.SECONDS, jitter = 500, retryOn = {ExternalServiceException.class}, abortOn = {IllegalArgumentException.class})
    @CircuitBreaker(requestVolumeThreshold = 10, delay = 30, delayUnit = ChronoUnit.SECONDS, successThreshold = 5, failOn = {ExternalServiceException.class})
    @CircuitBreakerName("fipe-api-circuit-breaker")
    @Timeout(value = 15, unit = ChronoUnit.SECONDS)
    @Fallback(fallbackMethod = "fetchModelsFallback")
    @Bulkhead(value = 5)
    public List<Model> fetchModelsByBrand(Brand brand) {
        try {
            FipeModelsWrapper response = fipeClient.getModels(brand.getCode());
            
            if (response == null || response.getModels() == null) {
                LOG.warnf("No models found for brand: %s", brand.getCode());
                return Collections.emptyList();
            }
            
            return response.getModels().stream()
                    .map(r -> new Model(r.getCode(), r.getName()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOG.errorf(e, "Error fetching models for brand: %s", brand.getCode());
            throw new ExternalServiceException("Failed to fetch models for brand: " + brand.getCode(), e);
        }
    }
    
    List<Model> fetchModelsFallback(Brand brand, ExternalServiceException e) {
        LOG.warnf("Fallback activated for fetching models of brand: %s due to: %s", brand.getCode(), e.getMessage());
        try {
            VehicleDataMessage message = new VehicleDataMessage(brand.getCode(), brand.getName());
            dlqPublisher.sendFallbackDlq(message, e.getMessage());
        } catch (Exception ex) {
            LOG.errorf(ex, "Failed to send DLQ message for brand: %s", brand.getCode());
        }
        return Collections.emptyList();
    }
}
