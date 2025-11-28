package com.fipe.infrastructure.adapter.in.rest.mapper;

import com.fipe.domain.model.Vehicle;
import com.fipe.infrastructure.adapter.in.rest.dto.VehicleDTO;

public class VehicleDTOMapper {
    
    public static VehicleDTO toDTO(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }
        
        return new VehicleDTO(
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
    
    public static Vehicle toDomain(VehicleDTO dto) {
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
