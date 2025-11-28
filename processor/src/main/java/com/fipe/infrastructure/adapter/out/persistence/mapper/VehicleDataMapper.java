package com.fipe.infrastructure.adapter.out.persistence.mapper;

import com.fipe.domain.model.VehicleData;
import com.fipe.infrastructure.adapter.out.persistence.entity.VehicleDataEntity;

public class VehicleDataMapper {
    
    public static VehicleDataEntity toEntity(VehicleData vehicleData) {
        if (vehicleData == null) {
            return null;
        }
        
        VehicleDataEntity entity = new VehicleDataEntity();
        entity.setId(vehicleData.getId());
        entity.setBrandCode(vehicleData.getBrandCode());
        entity.setBrandName(vehicleData.getBrandName());
        entity.setCode(vehicleData.getCode());
        entity.setModel(vehicleData.getModel());
        entity.setObservations(vehicleData.getObservations());
        entity.setCreatedAt(vehicleData.getCreatedAt());
        entity.setUpdatedAt(vehicleData.getUpdatedAt());
        
        return entity;
    }
    
    public static VehicleData toDomain(VehicleDataEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return new VehicleData(
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
