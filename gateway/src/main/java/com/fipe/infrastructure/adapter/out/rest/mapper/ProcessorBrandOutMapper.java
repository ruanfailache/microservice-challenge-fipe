package com.fipe.infrastructure.adapter.out.rest.mapper;

import com.fipe.domain.model.Brand;
import com.fipe.infrastructure.adapter.out.rest.dto.response.processor.ProcessorBrandOutResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface ProcessorBrandOutMapper {
    Brand toDomain(ProcessorBrandOutResponse vehicle);
    List<Brand> toDomain(List<ProcessorBrandOutResponse> vehicle);
}
