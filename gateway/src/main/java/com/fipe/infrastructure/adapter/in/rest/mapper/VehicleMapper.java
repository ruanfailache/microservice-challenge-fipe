package com.fipe.infrastructure.adapter.in.rest.mapper;

import com.fipe.domain.model.Vehicle;
import com.fipe.infrastructure.adapter.in.rest.dto.response.VehicleResponse;

public class VehicleMapper {
    
    public static VehicleResponse toDTO(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }
        
        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getBrandCode(),
                vehicle.getBrandName(),
                vehicle.getCode(),
                vehicle.getModel(),
                vehicle.getObservations(),
                vehicle.getCreatedAt(),
                vehicle.getUpdatedAt()
        );
    }
    
    public static Vehicle toDomain(VehicleResponse dto) {
        if (dto == null) {
            return null;
        }
        
        return new Vehicle(
                dto.getId(),
                dto.getBrandCode(),
                dto.getBrandName(),
                dto.getCode(),
                dto.getModel(),
                dto.getObservations(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }
}
