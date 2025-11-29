package com.fipe.domain.port.in.usecase;

import com.fipe.domain.model.Vehicle;

import java.util.List;

public interface VehicleQueryUseCase {
    
    List<Vehicle> getVehiclesByBrandCode(String brandCode);
    
    Vehicle getVehicleById(Long id);
}
