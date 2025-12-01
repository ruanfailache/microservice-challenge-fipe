package com.fipe.domain.port.in.usecase;

import com.fipe.domain.model.Vehicle;
import com.fipe.infrastructure.adapter.out.rest.dto.request.processor.ProcessorUpdateVehicleOutRequest;

import java.util.List;

public interface VehicleUseCase {
    List<Vehicle> getVehiclesByBrandCode(String authorization, String brandCode);

    Vehicle updateVehicle(String authorization, String brandCode, ProcessorUpdateVehicleOutRequest vehicleRequest);
}
