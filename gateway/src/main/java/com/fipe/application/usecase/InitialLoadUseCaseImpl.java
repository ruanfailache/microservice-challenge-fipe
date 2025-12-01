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

import java.util.List;

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
    public void executeInitialLoad(String authorization) {
        fipeClientPort.fetchAllBrands().forEach(brand -> {
            vehicleDataPublisherPort.publishBrandForProcessing(brand);
        });
    }
}
