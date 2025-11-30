package com.fipe.application.usecase;

import com.fipe.domain.model.Model;
import com.fipe.domain.model.VehicleData;
import com.fipe.domain.port.in.usecase.ProcessVehicleDataUseCase;
import com.fipe.domain.port.out.client.FipeClientPort;
import com.fipe.domain.port.out.repository.VehicleDataRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ProcessVehicleDataUseCaseImpl implements ProcessVehicleDataUseCase {
    
    private static final Logger LOG = Logger.getLogger(ProcessVehicleDataUseCaseImpl.class);
    
    @Inject
    FipeClientPort fipeClientPort;
    
    @Inject
    VehicleDataRepositoryPort vehicleDataRepositoryPort;
    
    @Override
    @Transactional
    public void processVehicleData(String brandCode, String brandName) {
        List<Model> models = fipeClientPort.fetchModelsByBrand(brandCode);

        if (models.isEmpty()) {
            return;
        }

        for (Model model : models) {
            try {
                if (vehicleDataRepositoryPort.exists(brandCode, model.getCode())) {
                    continue;
                }
                VehicleData vehicleData = new VehicleData(
                        null,
                        brandCode,
                        brandName,
                        model.getCode(),
                        model.getName(),
                        null,
                        LocalDateTime.now(),
                        LocalDateTime.now()
                );
                vehicleDataRepositoryPort.save(vehicleData);
            } catch (Exception e) {
                LOG.errorf(e, "Failed to save vehicle data for brand: %s, model: %s", brandCode, model.getCode());
            }
        }
    }
}
