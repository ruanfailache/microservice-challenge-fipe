package com.fipe.infrastructure.adapter.in.rest.mapper;

import com.fipe.domain.model.Brand;
import com.fipe.infrastructure.adapter.in.rest.dto.BrandDTO;

public class BrandDTOMapper {
    
    public static BrandDTO toDTO(Brand brand) {
        if (brand == null) {
            return null;
        }
        
        return new BrandDTO(brand.getCode(), brand.getName());
    }
    
    public static Brand toDomain(BrandDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return new Brand(dto.getCode(), dto.getName());
    }
}
