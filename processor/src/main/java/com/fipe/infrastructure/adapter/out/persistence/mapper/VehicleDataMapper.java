package com.fipe.infrastructure.adapter.out.persistence.mapper;

import com.fipe.domain.model.VehicleData;
import com.fipe.infrastructure.adapter.out.persistence.entity.VehicleDataEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface VehicleDataMapper {

    VehicleDataEntity toEntity(VehicleData vehicleData);

    VehicleData toDomain(VehicleDataEntity entity);
}
