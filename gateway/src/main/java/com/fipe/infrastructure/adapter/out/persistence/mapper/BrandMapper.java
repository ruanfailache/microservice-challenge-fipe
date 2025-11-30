package com.fipe.infrastructure.adapter.out.persistence.mapper;

import com.fipe.domain.model.Brand;
import com.fipe.infrastructure.adapter.out.persistence.entity.BrandEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface BrandMapper {

    BrandEntity toEntity(Brand brand);

    Brand toDomain(BrandEntity entity);
}
