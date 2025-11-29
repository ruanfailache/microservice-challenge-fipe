package com.fipe.domain.port.in.usecase;

import com.fipe.domain.model.Vehicle;

public interface VehicleUpdateUseCase {
    
    Vehicle updateVehicle(Long id, String model, String observations);
}
