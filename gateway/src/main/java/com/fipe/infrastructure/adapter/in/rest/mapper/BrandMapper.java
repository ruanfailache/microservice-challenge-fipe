package com.fipe.infrastructure.adapter.in.rest.mapper;

import com.fipe.domain.model.Brand;
import com.fipe.infrastructure.adapter.in.rest.dto.response.BrandResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface BrandMapper {

    BrandResponse toDTO(Brand brand);

    Brand toDomain(BrandResponse dto);
}
