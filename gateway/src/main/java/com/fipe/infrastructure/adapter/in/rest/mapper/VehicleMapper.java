package com.fipe.infrastructure.adapter.in.rest.mapper;

import com.fipe.domain.model.Vehicle;
import com.fipe.infrastructure.adapter.in.rest.dto.response.VehicleResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface VehicleMapper {

    VehicleResponse toDTO(Vehicle vehicle);

    Vehicle toDomain(VehicleResponse dto);
}
