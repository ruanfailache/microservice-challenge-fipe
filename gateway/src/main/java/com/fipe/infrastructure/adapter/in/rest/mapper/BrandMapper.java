package com.fipe.infrastructure.adapter.in.rest.mapper;

import com.fipe.domain.model.Brand;
import com.fipe.infrastructure.adapter.in.rest.dto.response.BrandResponse;

public class BrandMapper {
    
    public static BrandResponse toDTO(Brand brand) {
        if (brand == null) {
            return null;
        }
        
        return new BrandResponse(brand.getCode(), brand.getName());
    }
    
    public static Brand toDomain(BrandResponse dto) {
        if (dto == null) {
            return null;
        }
        
        return new Brand(dto.getCode(), dto.getName());
    }
}
