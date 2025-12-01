package com.fipe.infrastructure.adapter.out.rest.mapper;

import com.fipe.domain.model.Vehicle;
import com.fipe.infrastructure.adapter.out.rest.dto.response.processor.ProcessorVehicleOutResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface ProcessorVehicleOutMapper {
    Vehicle toDomain(ProcessorVehicleOutResponse vehicle);
}
