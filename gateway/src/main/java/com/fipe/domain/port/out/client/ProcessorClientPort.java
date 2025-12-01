package com.fipe.domain.port.out.client;

import com.fipe.domain.model.Brand;
import com.fipe.domain.model.Vehicle;
import com.fipe.infrastructure.adapter.out.rest.dto.request.processor.ProcessorUpdateVehicleOutRequest;

import java.util.List;
import java.util.Optional;

public interface ProcessorClientPort {
    List<Brand> getAllBrands(String authorization);

    Optional<Brand> getBrandByCode(String authorization, String code);

    List<Vehicle> getVehiclesByBrandCode(String authorization, String brandCode);

    Vehicle updateVehicle(String authorization, String brandCode, ProcessorUpdateVehicleOutRequest vehicleRequest);
}
