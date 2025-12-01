package com.fipe.application.usecase;

import com.fipe.domain.model.Brand;
import com.fipe.domain.port.in.usecase.BrandUseCase;
import com.fipe.domain.port.out.cache.VehicleCachePort;
import com.fipe.domain.port.out.client.ProcessorClientPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BrandUseCaseImpl implements BrandUseCase {
    
    private static final Logger LOG = Logger.getLogger(BrandUseCaseImpl.class);
    
    @Inject
    ProcessorClientPort processorClientPort;
    
    @Inject
    VehicleCachePort vehicleCachePort;
    
    public List<Brand> getAllBrands(String authorization) {
        return vehicleCachePort.getAllBrands().orElseGet(() -> {
            LOG.info("Fetching all brands from Rest Client");
            List<Brand> brands = processorClientPort.getAllBrands(authorization);
            vehicleCachePort.cacheAllBrands(brands);
            return brands;
        });
    }

    @Override
    public Optional<Brand> getBrandByCode(String authorization, String code) {
        LOG.info("Fetching brand by code from Rest Client");
        return processorClientPort.getBrandByCode(authorization, code);
    }
}
