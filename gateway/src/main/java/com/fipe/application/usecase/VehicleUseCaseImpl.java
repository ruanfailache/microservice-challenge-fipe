package com.fipe.application.usecase;

import com.fipe.domain.model.Vehicle;
import com.fipe.domain.port.in.usecase.VehicleUseCase;
import com.fipe.domain.port.out.cache.VehicleCachePort;
import com.fipe.domain.port.out.client.ProcessorClientPort;
import com.fipe.infrastructure.adapter.out.rest.dto.request.processor.ProcessorUpdateVehicleOutRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class VehicleUseCaseImpl implements VehicleUseCase {
    
    private static final Logger LOG = Logger.getLogger(VehicleUseCaseImpl.class);
    
    @Inject
    ProcessorClientPort processorClientPort;
    
    @Inject
    VehicleCachePort vehicleCachePort;

    @Override
    public List<Vehicle> getVehiclesByBrandCode(String authorization, String brandCode) {
        return vehicleCachePort.getVehiclesByBrandCode(brandCode).orElseGet(() -> {
            LOG.info("Fetching all vehicles from Rest Client");
            List<Vehicle> vehicles = processorClientPort.getVehiclesByBrandCode(authorization, brandCode);
            vehicleCachePort.cacheVehiclesByBrandCode(brandCode, vehicles);
            return vehicles;
        });
    }

    @Override
    public Vehicle updateVehicle(String authorization, String brandCode, ProcessorUpdateVehicleOutRequest vehicleRequest) {
        LOG.info("Updating vehicle via Rest Client");
        return processorClientPort.updateVehicle(authorization, brandCode, vehicleRequest);
    }
}
