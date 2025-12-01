package com.fipe.infrastructure.adapter.in.rest.mapper;

import com.fipe.domain.model.Vehicle;
import com.fipe.infrastructure.adapter.in.rest.dto.response.VehicleInResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface VehicleMapper {
    VehicleInResponse toDTO(Vehicle vehicle);
    List<VehicleInResponse> toDTO(List<Vehicle> vehicle);
}
