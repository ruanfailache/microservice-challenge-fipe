package com.fipe.domain.port.out.repository;

import com.fipe.domain.model.Brand;
import com.fipe.domain.model.VehicleData;

import java.util.List;
import java.util.Optional;

public interface VehicleDataRepositoryPort {
    
    List<VehicleData> saveAll(List<VehicleData> vehicleData);
    
    boolean exists(String brandCode, String code);

    List<VehicleData> findAllByBrandCode(String brandCode);

    Optional<VehicleData> findByBrandCodeAndCode(String brandCode, String code);

    VehicleData update(VehicleData vehicleData);

    Optional<VehicleData> findBrandByCode(String brandCode);

    List<Brand> findDistinctBrands();
}
