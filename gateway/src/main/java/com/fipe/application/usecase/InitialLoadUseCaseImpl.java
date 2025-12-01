package com.fipe.application.usecase;

import com.fipe.domain.model.Brand;
import com.fipe.domain.port.in.usecase.BrandUseCase;
import com.fipe.domain.port.in.usecase.InitialLoadUseCase;
import com.fipe.domain.port.out.client.FipeClientPort;
import com.fipe.domain.port.out.publisher.VehicleDataPublisherPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class InitialLoadUseCaseImpl implements InitialLoadUseCase {
    
    private static final Logger LOG = Logger.getLogger(InitialLoadUseCaseImpl.class);
    
    @Inject
    FipeClientPort fipeClientPort;
    
    @Inject
    VehicleDataPublisherPort vehicleDataPublisherPort;

    @Inject
    BrandUseCase brandUseCase;
    
    @Transactional
    public int executeInitialLoad(String authorization) {
        List<Brand> brands = fipeClientPort.fetchAllBrands();
    
        if (brands.isEmpty()) {
            LOG.warn("No brands found to process");
            return 0;
        }
        
        int processedCount = 0;

        for (Brand brand : brands) {
            Optional<Brand> foundBrand = brandUseCase.getBrandByCode(authorization, brand.getCode());
            if (foundBrand.isEmpty()) {
                vehicleDataPublisherPort.publishBrandForProcessing(brand);
                processedCount++;
            }
        }

        return processedCount;
    }
}
