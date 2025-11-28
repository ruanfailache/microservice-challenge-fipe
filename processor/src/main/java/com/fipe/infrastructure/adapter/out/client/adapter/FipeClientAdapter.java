package com.fipe.infrastructure.adapter.out.client.adapter;

import com.fipe.domain.exception.ExternalServiceException;
import com.fipe.domain.model.Model;
import com.fipe.domain.port.out.client.FipeClientPort;
import com.fipe.infrastructure.adapter.out.client.restclient.FipeRestClient;
import com.fipe.infrastructure.adapter.out.client.response.FipeModelsWrapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class FipeClientAdapter implements FipeClientPort {
    
    private static final Logger LOG = Logger.getLogger(FipeClientAdapter.class);
    
    @Inject
    @RestClient
    FipeRestClient fipeRestClient;
    
    @Override
    public List<Model> fetchModelsByBrand(String brandCode) {
        try {
            LOG.infof("Fetching models for brand: %s from FIPE API", brandCode);
            FipeModelsWrapper response = fipeRestClient.getModels(brandCode);
            
            if (response == null || response.getModels() == null) {
                LOG.warnf("No models found for brand: %s", brandCode);
                return Collections.emptyList();
            }
            
            List<Model> models = response.getModels().stream()
                    .map(r -> new Model(r.getCode(), r.getName()))
                    .collect(Collectors.toList());
            
            LOG.infof("Successfully fetched %d models for brand: %s", models.size(), brandCode);
            return models;
            
        } catch (Exception e) {
            LOG.errorf(e, "Error fetching models for brand: %s from FIPE API", brandCode);
            throw new ExternalServiceException("Failed to fetch models for brand: " + brandCode, e);
        }
    }
}
