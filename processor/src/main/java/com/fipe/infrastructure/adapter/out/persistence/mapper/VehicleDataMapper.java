package com.fipe.infrastructure.adapter.out.persistence.mapper;

import com.fipe.domain.model.VehicleData;
import com.fipe.infrastructure.adapter.out.persistence.entity.VehicleDataEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface VehicleDataMapper {

    VehicleDataEntity toEntity(VehicleData vehicleData);

    List<VehicleDataEntity> toEntity(List<VehicleData> vehiclesData);

    VehicleData toDomain(VehicleDataEntity entity);

    List<VehicleData> toDomain(List<VehicleDataEntity> entities);
}
