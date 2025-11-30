package com.fipe.application.usecase;

import com.fipe.domain.exception.NotFoundException;
import com.fipe.domain.model.Brand;
import com.fipe.domain.port.in.usecase.BrandQueryUseCase;
import com.fipe.domain.port.out.cache.VehicleCachePort;
import com.fipe.domain.port.out.repository.BrandRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class BrandQueryUseCaseImpl implements BrandQueryUseCase {
    
    private static final Logger LOG = Logger.getLogger(BrandQueryUseCaseImpl.class);
    
    @Inject
    BrandRepositoryPort brandRepositoryPort;
    
    @Inject
    VehicleCachePort vehicleCachePort;
    
    public List<Brand> getAllBrands() {
        return vehicleCachePort.getAllBrands().orElseGet(() -> {
            LOG.info("Fetching all brands from database");
            List<Brand> brands = brandRepositoryPort.findAll();
            vehicleCachePort.cacheAllBrands(brands);
            return brands;
        });
    }
    
    public Brand getBrandByCode(String code) {
        return brandRepositoryPort.findByCode(code).orElseThrow(() -> new NotFoundException("Brand", code));
    }

    public boolean existsBrandByCode(String code) {
        return brandRepositoryPort.findByCode(code).isPresent();
    }
}
