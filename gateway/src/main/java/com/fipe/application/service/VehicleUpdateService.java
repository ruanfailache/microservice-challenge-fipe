package com.fipe.application.service;

import com.fipe.domain.exception.NotFoundException;
import com.fipe.domain.model.Vehicle;
import com.fipe.domain.port.out.cache.VehicleCachePort;
import com.fipe.domain.port.out.repository.VehicleRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
public class VehicleUpdateService {
    
    private static final Logger LOG = Logger.getLogger(VehicleUpdateService.class);
    
    @Inject
    VehicleRepositoryPort vehicleRepositoryPort;
    
    @Inject
    VehicleCachePort vehicleCachePort;
    
    public Vehicle updateVehicle(Long id, String model, String observations) {
        LOG.infof("Updating vehicle with id: %d", id);
        
        Vehicle vehicle = vehicleRepositoryPort.findById(id)
                .orElseThrow(() -> new NotFoundException("Vehicle", String.valueOf(id)));
        
        vehicle.updateModel(model);
        vehicle.updateObservations(observations);
        
        Vehicle updated = vehicleRepositoryPort.save(vehicle);
        
        // Invalidate cache for this brand
        vehicleCachePort.invalidateVehicleCache(vehicle.getBrandCode());
        
        LOG.infof("Vehicle updated successfully: %d", id);
        
        return updated;
    }
}
