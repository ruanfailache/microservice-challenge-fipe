package com.fipe.infrastructure.adapter.out.persistence.mapper;

import com.fipe.domain.model.Brand;
import com.fipe.infrastructure.adapter.out.persistence.projection.BrandProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface BrandMapper {

    Brand toBrand(BrandProjection projection);
}
