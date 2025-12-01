package com.fipe.domain.port.in.usecase;

import com.fipe.domain.model.Brand;
import com.fipe.domain.model.VehicleData;

import java.util.List;
import java.util.Optional;

public interface ManageVehicleDataUseCase {

    List<Brand> getAllBrands();

    Optional<VehicleData> getBrandByCode(String brandCode);

    List<VehicleData> getVehiclesByBrand(String brandCode);

    Optional<VehicleData> getVehicleByBrandAndCode(String brandCode, String code);

    VehicleData updateVehicle(VehicleData vehicleData);
}
