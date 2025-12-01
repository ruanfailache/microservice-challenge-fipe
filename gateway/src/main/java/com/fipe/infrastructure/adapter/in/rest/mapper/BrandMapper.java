package com.fipe.infrastructure.adapter.in.rest.mapper;

import com.fipe.domain.model.Brand;
import com.fipe.infrastructure.adapter.in.rest.dto.response.BrandInResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface BrandMapper {

    BrandInResponse toDTO(Brand brand);

    List<BrandInResponse> toDTO(List<Brand> brands);

    Brand toDomain(BrandInResponse dto);
}
