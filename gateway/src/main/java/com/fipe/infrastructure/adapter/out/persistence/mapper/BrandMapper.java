package com.fipe.infrastructure.adapter.out.persistence.mapper;

import com.fipe.domain.model.Brand;
import com.fipe.infrastructure.adapter.out.persistence.entity.BrandEntity;

public class BrandMapper {
    
    public static BrandEntity toEntity(Brand brand) {
        if (brand == null) {
            return null;
        }
        
        BrandEntity entity = new BrandEntity();
        entity.setCode(brand.getCode());
        entity.setName(brand.getName());
        
        return entity;
    }
    
    public static Brand toDomain(BrandEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return new Brand(entity.getCode(), entity.getName());
    }
}
