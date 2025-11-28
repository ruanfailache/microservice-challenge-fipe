package com.fipe.application.service;

import com.fipe.domain.model.Brand;
import com.fipe.domain.port.out.cache.VehicleCachePort;
import com.fipe.domain.port.out.repository.BrandRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class BrandService {
    
    private static final Logger LOG = Logger.getLogger(BrandService.class);
    
    @Inject
    BrandRepositoryPort brandRepositoryPort;
    
    @Inject
    VehicleCachePort vehicleCachePort;
    
    public List<Brand> getAllBrands() {
        // Try to get from cache first
        return vehicleCachePort.getAllBrands()
                .orElseGet(() -> {
                    LOG.info("Fetching all brands from database");
                    List<Brand> brands = brandRepositoryPort.findAll();
                    vehicleCachePort.cacheAllBrands(brands);
                    return brands;
                });
    }
    
    public Brand getBrandByCode(String code) {
        return brandRepositoryPort.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Brand not found with code: " + code));
    }
}
