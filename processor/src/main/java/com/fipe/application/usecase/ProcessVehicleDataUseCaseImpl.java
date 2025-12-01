package com.fipe.application.usecase;

import com.fipe.domain.model.Brand;
import com.fipe.domain.model.Model;
import com.fipe.domain.model.VehicleData;
import com.fipe.domain.port.in.usecase.ProcessVehicleDataUseCase;
import com.fipe.domain.port.out.client.FipeClientPort;
import com.fipe.domain.port.out.repository.ProcessingFailureRepositoryPort;
import com.fipe.domain.port.out.repository.VehicleDataRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ProcessVehicleDataUseCaseImpl implements ProcessVehicleDataUseCase {

    @Inject
    FipeClientPort fipeClientPort;
    
    @Inject
    VehicleDataRepositoryPort vehicleDataRepositoryPort;
    
    @Override
    @Transactional
    public List<VehicleData> processVehicleData(String brandCode, String brandName) {
        List<Model> models = fipeClientPort.fetchModelsByBrand(new Brand(brandCode, brandName));

        if (models.isEmpty()) {
            return List.of();
        }

        List<VehicleData> vehiclesData = new ArrayList<>();

        for (Model model : models) {
            if (vehicleDataRepositoryPort.exists(brandCode, model.getCode())) {
                continue;
            }
            VehicleData vehicleData = VehicleData.create(
                    brandCode,
                    brandName,
                    model.getCode(),
                    model.getName()
            );
            vehiclesData.add(vehicleData);
        }

        return vehicleDataRepositoryPort.saveAll(vehiclesData);
    }
}
