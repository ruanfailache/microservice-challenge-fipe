package com.fipe.application.service;

import com.fipe.domain.model.Vehicle;
import com.fipe.domain.port.out.cache.VehicleCachePort;
import com.fipe.domain.port.out.repository.VehicleRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class VehicleQueryService {
    
    private static final Logger LOG = Logger.getLogger(VehicleQueryService.class);
    
    @Inject
    VehicleRepositoryPort vehicleRepositoryPort;
    
    @Inject
    VehicleCachePort vehicleCachePort;
    
    public List<Vehicle> getVehiclesByBrandCode(String brandCode) {
        // Try to get from cache first
        return vehicleCachePort.getVehiclesByBrandCode(brandCode)
                .orElseGet(() -> {
                    LOG.infof("Fetching vehicles from database for brand: %s", brandCode);
                    List<Vehicle> vehicles = vehicleRepositoryPort.findByBrandCode(brandCode);
                    vehicleCachePort.cacheVehiclesByBrandCode(brandCode, vehicles);
                    return vehicles;
                });
    }
    
    public Vehicle getVehicleById(Long id) {
        return vehicleRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
    }
}
