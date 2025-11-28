package com.fipe.domain.port.out.repository;

import com.fipe.domain.model.Vehicle;
import java.util.List;
import java.util.Optional;

public interface VehicleRepositoryPort {
    
    List<Vehicle> findByBrandCode(String brandCode);
    
    Optional<Vehicle> findById(Long id);
    
    Vehicle save(Vehicle vehicle);
    
    void deleteById(Long id);
    
    boolean existsByBrandCodeAndCode(String brandCode, String code);
}
