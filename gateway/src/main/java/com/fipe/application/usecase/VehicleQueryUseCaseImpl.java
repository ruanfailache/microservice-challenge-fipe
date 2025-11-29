package com.fipe.application.usecase;

import com.fipe.domain.exception.NotFoundException;
import com.fipe.domain.model.Vehicle;
import com.fipe.domain.port.in.usecase.VehicleQueryUseCase;
import com.fipe.domain.port.out.cache.VehicleCachePort;
import com.fipe.domain.port.out.repository.VehicleRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class VehicleQueryUseCaseImpl implements VehicleQueryUseCase {
    
    private static final Logger LOG = Logger.getLogger(VehicleQueryUseCaseImpl.class);
    
    @Inject
    VehicleRepositoryPort vehicleRepositoryPort;
    
    @Inject
    VehicleCachePort vehicleCachePort;
    
    public List<Vehicle> getVehiclesByBrandCode(String brandCode) {
        return vehicleCachePort.getVehiclesByBrandCode(brandCode).orElseGet(() -> {
            LOG.infof("Fetching vehicles from database for brand: %s", brandCode);
            List<Vehicle> vehicles = vehicleRepositoryPort.findByBrandCode(brandCode);
            vehicleCachePort.cacheVehiclesByBrandCode(brandCode, vehicles);
            return vehicles;
        });
    }
    
    public Vehicle getVehicleById(Long id) {
        return vehicleRepositoryPort.findById(id).orElseThrow(() -> new NotFoundException("Vehicle", String.valueOf(id)));
    }
}
