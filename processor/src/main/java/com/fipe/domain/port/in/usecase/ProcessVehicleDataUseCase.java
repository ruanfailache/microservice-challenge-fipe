package com.fipe.domain.port.in.usecase;

import com.fipe.domain.model.VehicleData;

import java.util.List;

public interface ProcessVehicleDataUseCase {
    
    List<VehicleData> processVehicleData(String brandCode, String brandName);
}
