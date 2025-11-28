package com.fipe.domain.port.out.repository;

import com.fipe.domain.model.VehicleData;

public interface VehicleDataRepositoryPort {
    
    VehicleData save(VehicleData vehicleData);
    
    boolean exists(String brandCode, String code);
}
