package com.fipe.infrastructure.adapter.out.client.adapter;

import com.fipe.domain.exception.ExternalServiceException;
import com.fipe.domain.model.Brand;
import com.fipe.domain.port.out.client.FipeClientPort;
import com.fipe.infrastructure.adapter.out.client.restclient.FipeRestClient;
import com.fipe.infrastructure.adapter.out.client.response.FipeBrandResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class FipeClientAdapter implements FipeClientPort {
    
    private static final Logger LOG = Logger.getLogger(FipeClientAdapter.class);
    
    @Inject
    @RestClient
    FipeRestClient fipeRestClient;
    
    @Override
    public List<Brand> fetchAllBrands() {
        try {
            LOG.info("Fetching all brands from FIPE API");
            List<FipeBrandResponse> response = fipeRestClient.getBrands();
            
            List<Brand> brands = response.stream()
                    .map(r -> new Brand(r.getCode(), r.getName()))
                    .collect(Collectors.toList());
            
            LOG.infof("Successfully fetched %d brands from FIPE API", brands.size());
            return brands;
            
        } catch (Exception e) {
            LOG.error("Error fetching brands from FIPE API", e);
            throw new ExternalServiceException("Failed to fetch brands from FIPE API", e);
        }
    }
}
