package com.fipe.infrastructure.adapter.in.rest.mapper;

import com.fipe.domain.model.Brand;
import com.fipe.infrastructure.adapter.in.rest.dto.response.BrandInResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface BrandMapper {

    BrandInResponse toDTO(Brand brand);

    Brand toDomain(BrandInResponse dto);
}
