package com.fipe.application.usecase;

import com.fipe.domain.exception.NotFoundException;
import com.fipe.domain.model.Brand;
import com.fipe.domain.port.in.usecase.BrandQueryUseCase;
import com.fipe.domain.port.in.usecase.InitialLoadUseCase;
import com.fipe.domain.port.out.client.FipeClientPort;
import com.fipe.domain.port.out.publisher.VehicleDataPublisherPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class InitialLoadUseCaseImpl implements InitialLoadUseCase {
    
    private static final Logger LOG = Logger.getLogger(InitialLoadUseCaseImpl.class);
    
    @Inject
    FipeClientPort fipeClientPort;
    
    @Inject
    VehicleDataPublisherPort vehicleDataPublisherPort;

    @Inject
    BrandQueryUseCase brandQueryUseCase;
    
    @Override
    @Transactional
    public int executeInitialLoad() {
        LOG.info("Starting initial load process");
        
        List<Brand> brands = fipeClientPort.fetchAllBrands();
    
        if (brands.isEmpty()) {
            LOG.warn("No brands found to process");
            return 0;
        }
        
        LOG.infof("Found %d brands to process", brands.size());
        
        int processedCount = 0;

        for (Brand brand : brands) {
            try {
                boolean isBrandRegistered = brandQueryUseCase.existsBrandByCode(brand.getCode());
                if (!isBrandRegistered) {
                    vehicleDataPublisherPort.publishBrandForProcessing(brand);
                    processedCount++;
                } else {
                    LOG.warnf("Brand \"%s\" is already registered", brand.getCode());
                }
            } catch (Exception e) {
                LOG.errorf(e, "Failed to publish brand: %s", brand.getCode());
            }
        }

        LOG.infof("Initial load completed. Published %d brands for processing", processedCount);
        return processedCount;
    }
}
