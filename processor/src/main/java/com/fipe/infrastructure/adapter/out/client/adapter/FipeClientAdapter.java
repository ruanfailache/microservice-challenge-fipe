package com.fipe.infrastructure.adapter.out.client.adapter;

import com.fipe.domain.exception.ExternalServiceException;
import com.fipe.domain.model.Model;
import com.fipe.domain.port.out.client.FipeClientPort;
import com.fipe.infrastructure.adapter.out.client.restclient.FipeRestClient;
import com.fipe.infrastructure.adapter.out.client.response.FipeModelsWrapper;
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
    FipeRestClient restClient;
    
    @Override
    @Retry(maxRetries = 3, delay = 2, delayUnit = ChronoUnit.SECONDS, jitter = 500,
           retryOn = {ExternalServiceException.class}, abortOn = {IllegalArgumentException.class})
    @CircuitBreaker(requestVolumeThreshold = 10, failureRatio = 0.5, delay = 30, 
                   delayUnit = ChronoUnit.SECONDS, successThreshold = 5, 
                   failOn = {ExternalServiceException.class})
    @CircuitBreakerName("fipe-api-circuit-breaker")
    @Timeout(value = 15, unit = ChronoUnit.SECONDS)
    @Fallback(fallbackMethod = "fetchModelsFallback")
    @Bulkhead(value = 5, waitingTaskQueue = 10)
    public List<Model> fetchModelsByBrand(String brandCode) {
        LOG.infof("Fetching models for brand: %s", brandCode);
        
        try {
            FipeModelsWrapper response = restClient.getModels(brandCode);
            
            if (response == null || response.getModels() == null) {
                LOG.warnf("No models found for brand: %s", brandCode);
                return Collections.emptyList();
            }
            
            List<Model> models = response.getModels().stream()
                    .map(r -> new Model(r.getCode(), r.getName()))
                    .collect(Collectors.toList());
            
            LOG.infof("Fetched %d models for brand: %s", models.size(), brandCode);
            return models;
            
        } catch (Exception e) {
            LOG.errorf(e, "Error fetching models for brand: %s", brandCode);
            throw new ExternalServiceException("Failed to fetch models for brand: " + brandCode, e);
        }
    }
    
    List<Model> fetchModelsFallback(String brandCode) {
        LOG.warnf("Fallback activated for brand: %s", brandCode);
        return Collections.emptyList();
    }
}
