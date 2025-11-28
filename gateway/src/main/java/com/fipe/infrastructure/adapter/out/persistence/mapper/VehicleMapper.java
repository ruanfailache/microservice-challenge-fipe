package com.fipe.infrastructure.adapter.out.persistence.mapper;

import com.fipe.domain.model.Vehicle;
import com.fipe.infrastructure.adapter.out.persistence.entity.VehicleEntity;

public class VehicleMapper {
    
    public static VehicleEntity toEntity(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }
        
        VehicleEntity entity = new VehicleEntity();
        entity.setId(vehicle.getId());
        entity.setBrandCode(vehicle.getBrandCode());
        entity.setBrandName(vehicle.getBrandName());
        entity.setCode(vehicle.getCode());
        entity.setModel(vehicle.getModel());
        entity.setObservations(vehicle.getObservations());
        entity.setCreatedAt(vehicle.getCreatedAt());
        entity.setUpdatedAt(vehicle.getUpdatedAt());
        
        return entity;
    }
    
    public static Vehicle toDomain(VehicleEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return new Vehicle(
                entity.getId(),
                entity.getBrandCode(),
                entity.getBrandName(),
                entity.getCode(),
                entity.getModel(),
                entity.getObservations(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
