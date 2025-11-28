package com.fipe.domain.port.out.cache;

import com.fipe.domain.model.Brand;
import com.fipe.domain.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleCachePort {
    
    Optional<List<Brand>> getAllBrands();
    
    void cacheAllBrands(List<Brand> brands);
    
    Optional<List<Vehicle>> getVehiclesByBrandCode(String brandCode);
    
    void cacheVehiclesByBrandCode(String brandCode, List<Vehicle> vehicles);
    
    void invalidateVehicleCache(String brandCode);
    
    void invalidateAllCaches();
}
