package com.fipe.infrastructure.adapter.out.persistence.mapper;

import com.fipe.domain.model.VehicleData;
import com.fipe.infrastructure.adapter.out.persistence.entity.VehicleDataEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface VehicleDataMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    VehicleDataEntity toEntity(VehicleData vehicleData);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "brandCode", source = "brandCode")
    @Mapping(target = "brandName", source = "brandName")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "model", source = "model")
    @Mapping(target = "observations", source = "observations")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", source = "updatedAt")
    VehicleData toDomain(VehicleDataEntity entity);
}
